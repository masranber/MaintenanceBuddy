package com.example.maintenancebuddy.data.model;

import java.util.ArrayList;

public class Garage {

    private String uid;
    private String userUID;
    private String description;
    private String name;
    private String image;

    public Garage() {}

    public Garage(String userUID, String name){
        this.userUID = userUID;
        this.name = name;
    }

    public String getUID() {
        return uid;
    }

    public void setUID(String uid) {
        this.uid = uid;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Garage createForUser(String userUID, String name) {
        Garage garage = new Garage(userUID, name);
        garage.uid = userUID;
        return garage;
    }
}
