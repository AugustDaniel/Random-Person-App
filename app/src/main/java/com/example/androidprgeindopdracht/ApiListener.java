package com.example.androidprgeindopdracht;

public interface ApiListener {
    void onAvailable(Person person);
    void onError(Error error);
}
