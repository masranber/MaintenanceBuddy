package com.example.maintenancebuddy.data.model;

import com.example.maintenancebuddy.data.MaintenanceType;

import java.util.Date;
import java.util.List;

public class MaintenanceEvent {

    private String uid;
    private String vehicleUID;
    private long datePerformed;
    private double vehicleOdometer;
    private double engineHours;
    private String description;
    private String reason;
    private int maintenanceType;
    private double totalCost;

    //private boolean isMonthly; // added field for calendar implementation--using class to also represent future maintenance plans

    //private List<MaintenancePart> partsReplaced;
    private String receiptImagePath;
    //private List<String> otherImagePaths;
    //private List<String> otherDocumentPaths;

    public MaintenanceEvent() {}

    public String getUID() {
        return uid;
    }

    public void setUID(String uid) {
        this.uid = uid;
    }

    public String getVehicleUID() {
        return vehicleUID;
    }

    public void setVehicleUID(String vehicleUID) {
        this.vehicleUID = vehicleUID;
    }

    public long getDatePerformed() {
        return this.datePerformed;
    }

    /*public void setMonthly(Boolean isMonthly) { this.isMonthly = isMonthly; }

    public boolean isMonthly() { return isMonthly; }

     */

    public void setDatePerformed(long datePerformed) {
        this.datePerformed = datePerformed;
    }

    public double getVehicleOdometer() {
        return vehicleOdometer;
    }

    public void setVehicleOdometer(double vehicleOdometer) {
        this.vehicleOdometer = vehicleOdometer;
    }

    public double getEngineHours() {
        return engineHours;
    }

    public void setEngineHours(double engineHours) {
        this.engineHours = engineHours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(int maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    /*public List<MaintenancePart> getPartsReplaced() {
        return partsReplaced;
    }

    public void setPartsReplaced(List<MaintenancePart> partsReplaced) {
        this.partsReplaced = partsReplaced;
    }*/

    public String getReceiptImagePath() {
        return receiptImagePath;
    }

    public void setReceiptImagePath(String receiptImagePath) { this.receiptImagePath = receiptImagePath; }

    /*public List<String> getOtherImagePaths() {
        return otherImagePaths;
    }

    public void setOtherImagePaths(List<String> otherImagePaths) { this.otherImagePaths = otherImagePaths; }

    public List<String> getOtherDocumentPaths() {
        return otherDocumentPaths;
    }

    public void setOtherDocumentPaths(List<String> otherDocumentPaths) {
        this.otherDocumentPaths = otherDocumentPaths;
    }*/

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
