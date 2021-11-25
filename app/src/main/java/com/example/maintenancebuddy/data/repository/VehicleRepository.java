package com.example.maintenancebuddy.data.repository;

import com.example.maintenancebuddy.data.model.Vehicle;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class VehicleRepository {

    private Vehicle currentVehicle;

    @Inject
    public VehicleRepository() {
        currentVehicle = null;
    }

    public void setCurrentVehicle(Vehicle vehicle) {
        this.currentVehicle = vehicle;
    }

    public Vehicle getCurrentVehicle() {
        return this.currentVehicle;
    }

}
