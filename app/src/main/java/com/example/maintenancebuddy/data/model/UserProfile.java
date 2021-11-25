package com.example.maintenancebuddy.data.model;

public class UserProfile {

    public String uid;
    public String firstName;
    public String lastName;

    public UserProfile() {}

    public UserProfile(String uid, String firstName, String lastName) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserProfile(UserProfile copy) {
        uid = copy.uid;
        firstName = copy.firstName;
        lastName = copy.lastName;
    }


    public String getFullName() {
        return firstName + " " + lastName;
    }


}
