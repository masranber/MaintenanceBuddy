package com.example.maintenancebuddy.ui.recyclerview;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class MaintenanceTypeAdapter extends ArrayAdapter<String> {

    public MaintenanceTypeAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public MaintenanceTypeAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }
}
