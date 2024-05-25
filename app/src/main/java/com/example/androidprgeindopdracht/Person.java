package com.example.androidprgeindopdracht;

import java.io.Serializable;

public class Person implements Serializable {

    public static final String TAG = "PERSON_TAG";

    String gender;
    String firstName;
    String lastName;
    int houseNumber;
    String street;
    String city;
    String country;
    String email;
    String birthDate;
    String phone;
    String imageUrl;
    String nationality;

    public Person(String gender, String firstName, String lastName, int houseNumber, String street, String city, String country, String email, String birthDate, String phone, String imageUrl, String nationality) {
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.houseNumber = houseNumber;
        this.street = street;
        this.city = city;
        this.country = country;
        this.email = email;
        this.birthDate = birthDate;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.nationality = nationality;
    }
}
