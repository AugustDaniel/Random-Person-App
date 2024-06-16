package com.example.androidprgeindopdracht;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiManager {

    private static final int amountOfPersons = 5;
    private static final String url = "https://randomuser.me/api/?results=" + amountOfPersons;
    private OkHttpClient client;
    private ApiListener listener;

    public ApiManager(ApiListener listener) {
        this.listener = listener;
        this.client = new OkHttpClient();
    }

    public void getPersons() {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle the error
                new Handler(Looper.getMainLooper()).post(() -> listener.onError(new Error(e.getLocalizedMessage())));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("cool", "onResponseCalled");
                if (!response.isSuccessful()) {
                    new Handler(Looper.getMainLooper()).post(() -> listener.onError(new Error(response.message())));
                    return;
                }

                try {
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

                        String gender = object.getString("gender");
                        String firstName = name.getString("first");
                        String lastName = name.getString("last");
                        int number = street.getInt("number");
                        String streetName = street.getString("name");
                        String city = location.getString("city");
                        String country = location.getString("country");
                        String email = object.getString("email");
                        String birthDate = dob.getString("date");
                        String phone = object.getString("phone");
                        String image = picture.getString("large");
                        String nationality = object.getString("nat");

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

                        new Handler(Looper.getMainLooper()).post(() -> listener.onAvailable(person));
                    }
                } catch (JSONException e) {
                    new Handler(Looper.getMainLooper()).post(() -> listener.onError(new Error(e.getLocalizedMessage())));
                }
            }
        });
    }
}
