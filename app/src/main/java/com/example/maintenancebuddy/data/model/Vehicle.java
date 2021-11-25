package com.example.maintenancebuddy.data.model;

import java.util.ArrayList;

public class Vehicle {
    String make;
    int year;
    String model;
    String color;
    String vin;
    String userUID;
    String garageUID;
    int miles;
    String pictureURL;
    String uid;

    private boolean hasOdometer;

    //public ArrayList<MaintenanceEvent> upcomingMaintenance;

    public Vehicle() {}

    public Vehicle(String userID, String make, String model, int year){
        this.userUID = userID;
        this.make = make;
        this.model = model;
        this.year = year;
        //upcomingMaintenance = new ArrayList<MaintenanceEvent>();
    }

    /*public void addUpcomingMaintenance(MaintenanceEvent newMaintenance) { upcomingMaintenance.add(newMaintenance); }

    public ArrayList<MaintenanceEvent> getUpcomingMaintenance() {
        return upcomingMaintenance;
    }*/

    public String getUID() {
        return uid;
    }

    public void setUID(String uid) {
        this.uid = uid;
    }

    public String getGarageUID() {
        return garageUID;
    }

    public void setGarageUID(String garageUID) {
        this.garageUID = garageUID;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userID) {
        this.userUID = userID;
    }

    public String getMake() {
        return make;
    }

    public void setMiles(int miles) {this.miles = miles;}

    public int getMiles() {return this.miles;}

    public void setMake(String make) {
        this.make = make;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getPictureURL() { return pictureURL; }

    public void setPictureURL(String pictureURL) { this.pictureURL = pictureURL; }

    public boolean hasOdometer() {
        return hasOdometer;
    }

    public void setHasOdometer(boolean hasOdometer) {
        this.hasOdometer = hasOdometer;
    }
}
