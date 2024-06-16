package com.example.androidprgeindopdracht;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Person implements Parcelable {

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

    protected Person(Parcel in) {
        gender = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        houseNumber = in.readInt();
        street = in.readString();
        city = in.readString();
        country = in.readString();
        email = in.readString();
        birthDate = in.readString();
        phone = in.readString();
        imageUrl = in.readString();
        nationality = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return MainActivity.context.getString(R.string.person_to_string_format,
                gender, firstName, lastName, houseNumber, street, city, country, email, birthDate, phone, imageUrl, nationality);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(gender);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeInt(houseNumber);
        dest.writeString(street);
        dest.writeString(city);
        dest.writeString(country);
        dest.writeString(email);
        dest.writeString(birthDate);
        dest.writeString(phone);
        dest.writeString(imageUrl);
        dest.writeString(nationality);
    }
}
