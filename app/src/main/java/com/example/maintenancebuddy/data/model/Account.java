package com.example.maintenancebuddy.data.model;

import androidx.annotation.Nullable;

public class Account {

    private final String uid;
    private final String firstName;
    private final String lastName;
    private final String fullName;

    protected Account(String uid, String firstName, String lastName) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = String.format("%s %s", firstName, lastName);
    }

    public String getUID() {
        return uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(!(obj instanceof Account)) return false;
        return ((Account) obj).uid.equals(this.uid);
    }
}
