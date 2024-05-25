package com.example.androidprgeindopdracht;

import java.util.ArrayList;
import java.util.List;

public class ApiHelper {
    public static final ApiHelper helper = new ApiHelper();
    private boolean startup = true;
    final List<Person> list = new ArrayList<>();

    private ApiHelper() {}

    public void execute(ApiManager api) {
        if (startup) {
            api.getPersons();
            startup = false;
        }
    }
}
