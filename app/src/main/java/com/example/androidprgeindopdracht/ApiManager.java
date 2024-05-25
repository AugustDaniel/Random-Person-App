package com.example.androidprgeindopdracht;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ApiManager {

    private static final int amountOfPersons = 50;
    private static final String url = "https://randomuser.me/api/?results=" + amountOfPersons;

    private Context appContext;
    private RequestQueue queue;
    private ApiListener listener;

    public ApiManager(Context appContext, ApiListener listener) {
        this.appContext = appContext;
        this.listener = listener;
        this.queue = Volley.newRequestQueue(this.appContext);
    }

    public void getPersons(List<Person> persons) {
        if (persons.size() >= amountOfPersons) {
            return;
        }

        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, // Use HTTP GET to retrieve the data from the NASA API
                url, // This is the actual URL used to retrieve the data
                null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject object = results.getJSONObject(i);
                            String gender = object.getString("gender");
                            String firstName = object.getJSONObject("name").getString("first");
                            String lastName = object.getJSONObject("name").getString("last");
                            int number = object.getJSONObject("location").getJSONObject("street").getInt("number");
                            String street = object.getJSONObject("location").getJSONObject("street").getString("name");
                            String city = object.getJSONObject("location").getString("city");
                            String country = object.getJSONObject("location").getString("country");
                            String email = object.getString("email");
                            String birthDate = object.getJSONObject("dob").getString("date");
                            String phone = object.getString("phone");
                            String image = object.getJSONObject("picture").getString("large");
                            String nationality = object.getString("nat");
                            listener.onAvailable(new Person(
                                    gender,
                                    firstName,
                                    lastName,
                                    number,
                                    street,
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
        this.queue.add(request);
    }
}
