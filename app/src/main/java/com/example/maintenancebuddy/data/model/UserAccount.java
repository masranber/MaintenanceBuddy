package com.example.maintenancebuddy.data.model;

public class UserAccount extends Account {

    private final String emailAddress;
    private final long creationTimestamp;
    private final long lastSignInTimestamp;

    public UserAccount(String uid, String emailAddress, long creationTimestamp, long lastSignInTimestamp, String firstName, String lastName) {
        super(uid, firstName, lastName);
        this.emailAddress = emailAddress;
        this.creationTimestamp = creationTimestamp;
        this.lastSignInTimestamp = lastSignInTimestamp;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public long getLastSignInTimestamp() {
        return lastSignInTimestamp;
    }
}
