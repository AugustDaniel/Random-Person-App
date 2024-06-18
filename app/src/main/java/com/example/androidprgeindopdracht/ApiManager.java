package com.example.androidprgeindopdracht;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiManager {

    private static final String TAG = "ApiManager";
    private static final int amountOfPersons = 5;
    private static final String url = "https://randomuser.me/api/?results=" + amountOfPersons;
    private OkHttpClient client;
    private ApiListener listener;
    private Context context;

    public ApiManager(ApiListener listener, Context context) {
        this.listener = listener;
        this.client = new OkHttpClient();
        this.context = context;
    }

    public void getPersons() {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "call failure " + e.getMessage());
                new Handler(Looper.getMainLooper()).post(() -> listener.onError(new Error(e.getLocalizedMessage())));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d(TAG, "onResponseCalled");
                if (!response.isSuccessful()) {
                    Log.e(TAG, "response not successful");
                    new Handler(Looper.getMainLooper()).post(() -> listener.onError(new Error(response.message())));
                    return;
                }

                try {
                    if (response.body() == null) {
                        return;
                    }

                    String jsonData = response.body().string();
                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONArray results = jsonObject.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject object = results.getJSONObject(i);

                        JSONObject name = object.getJSONObject("name");
                        JSONObject location = object.getJSONObject("location");
                        JSONObject street = location.getJSONObject("street");
                        JSONObject dob = object.getJSONObject("dob");
                        JSONObject picture = object.getJSONObject("picture");

                        String gender = getTranslatedGender(object.getString("gender"));
                        String firstName = name.getString("first");
                        String lastName = name.getString("last");
                        int number = street.getInt("number");
                        String streetName = street.getString("name");
                        String city = location.getString("city");
                        String email = object.getString("email");
                        String birthDate = dob.getString("date");
                        String phone = object.getString("phone");
                        String image = picture.getString("large");
                        String nationality = object.getString("nat");
                        String country = new Locale("", nationality).getDisplayCountry(context.getResources().getConfiguration().getLocales().get(0));

                        Person person = new Person(
                                gender,
                                firstName,
                                lastName,
                                number,
                                streetName,
                                city,
                                country,
                                email,
                                birthDate,
                                phone,
                                image,
                                nationality
                        );

                        Log.d(TAG, "person created");
                        new Handler(Looper.getMainLooper()).post(() -> listener.onAvailable(person));
                    }
                } catch (JSONException e) {
                    Log.i(TAG, "json error " + e.getMessage());
                    new Handler(Looper.getMainLooper()).post(() -> listener.onError(new Error(e.getLocalizedMessage())));
                }
            }
        });
    }

    public String getTranslatedGender(String genderKey) {
        int resId = context.getResources().getIdentifier(genderKey + "_gender_text", "string", context.getPackageName());
        if (resId == 0) {
            return genderKey;
        }
        return context.getString(resId);
    }
}
