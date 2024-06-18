package com.example.androidprgeindopdracht;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ApiHelper {
    private static final String TAG = "ApiHelper";
    public static final ApiHelper helper = new ApiHelper();
    private boolean startup = true;
    final List<Person> list = new ArrayList<>();

    private ApiHelper() {}

    public void execute(ApiManager api) {
        Log.d(TAG, "execute called");
        if (startup) {
            Log.d(TAG, "execute called on startup");
            api.getPersons();
            startup = false;
        }
    }
}
