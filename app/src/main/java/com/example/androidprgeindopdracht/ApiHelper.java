package com.example.androidprgeindopdracht;

public class ApiHelper {
    public static final ApiHelper helper = new ApiHelper();
    private boolean startup = true;

    private ApiHelper() {}

    public void execute(ApiManager api) {
        if (startup) {
            api.getPersons();
            startup = false;
        }
    }
}
