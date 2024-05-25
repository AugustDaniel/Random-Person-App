package com.example.androidprgeindopdracht;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ApiManager {

    private static final int amountOfPersons = 10;
    private static final String url = "https://randomuser.me/api/?results=" + amountOfPersons;

    private Context appContext;
    private RequestQueue queue;
    private ApiListener listener;

    public ApiManager(Context appContext, ApiListener listener) {
        this.appContext = appContext;
        this.listener = listener;
        this.queue = Volley.newRequestQueue(this.appContext);
    }

    public void getPersons() {
        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, // Use HTTP GET to retrieve the data from the NASA API
                url, // This is the actual URL used to retrieve the data
                null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject object = results.getJSONObject(i);

                            // Extract nested objects once
                            JSONObject name = object.getJSONObject("name");
                            JSONObject location = object.getJSONObject("location");
                            JSONObject street = location.getJSONObject("street");
                            JSONObject dob = object.getJSONObject("dob");
                            JSONObject picture = object.getJSONObject("picture");

                            // Extract fields
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

                            listener.onAvailable(new Person(
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
                            ));
                        }
                    } catch (JSONException exception) {
                        // Make sure to handle any errors, at least provide a log entry
                    }
                },
                error -> {
                    // Handle the error
                    listener.onError(new Error(error.getLocalizedMessage()));
                }
        );
        // Add the request that was just created to the request queue, this starts off the actual netwerk transmission
        Log.d("cool", "cool");
        this.queue.add(request);
    }
}
