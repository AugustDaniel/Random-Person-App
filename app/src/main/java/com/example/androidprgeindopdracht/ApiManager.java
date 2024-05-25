package com.example.androidprgeindopdracht;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiManager {

    private static final String url = "https://randomuser.me/api/?results=1";

    private Context appContext;
    private RequestQueue queue;
    private ApiListener listener;

    public ApiManager(Context appContext, ApiListener listener) {
        this.appContext = appContext;
        this.listener = listener;
        this.queue = Volley.newRequestQueue(this.appContext);
    }

    public void getPersons() {
//        final JsonObjectRequest request = new JsonObjectRequest(
//                Request.Method.GET, // Use HTTP GET to retrieve the data from the NASA API
//                url, // This is the actual URL used to retrieve the data
//                null,
//                response -> {
//                    try {
//                        JSONArray photosJsonArray = null; // TODO Change
//                        for (int i = 0; i < photosJsonArray.length(); i++) {
//                            JSONObject photoJsonObject = null; // TODO Change
//                            String id = photoJsonObject.getString("dummy");
//                            String imageUrl = photoJsonObject.getString("dummy");
//
//                            Person person = new Person("test", "test","test",2,"test","test","test","test","test","test","test","test");
//                            listener.onAvailable(person);
//                        }
//                    } catch (JSONException exception) {
//                        // Make sure to handle any errors, at least provide a log entry
//                    }
//                },
//                error -> {
//                    // Handle the error
//                    listener.onError(new Error(error.getLocalizedMessage()));
//                }
//        );
//        // Add the request that was just created to the request queue, this starts off the actual netwerk transmission
//        this.queue.add(request);

        Person person = new Person("test", "test","test",2,"test","test","test","test","test","test","test","test");
        listener.onAvailable(person);
    }
}
