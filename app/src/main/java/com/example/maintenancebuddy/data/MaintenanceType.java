package com.example.maintenancebuddy.data;

import androidx.annotation.NonNull;

public enum MaintenanceType {
    PREVENTATIVE, REPAIR, MODIFICATION;


    @NonNull
    @Override
    public String toString() {
        String str = super.toString();
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase(); // Only first letter capitalized
    }
}
