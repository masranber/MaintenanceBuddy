package com.example.maintenancebuddy.data.model;

public class Event {
        String reciptURL;
        String Cost;
        String partsList;
        String additionDetails;

    public String getReciptURL() {
        return reciptURL;
    }

    public void setReciptURL(String reciptURL) {
        this.reciptURL = reciptURL;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getPartsList() {
        return partsList;
    }

    public void setPartsList(String partsList) {
        this.partsList = partsList;
    }

    public String getAdditionDetails() {
        return additionDetails;
    }

    public void setAdditionDetails(String additionDetails) {
        this.additionDetails = additionDetails;
    }
}
