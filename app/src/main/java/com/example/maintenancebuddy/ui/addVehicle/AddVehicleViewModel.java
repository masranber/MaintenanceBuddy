package com.example.maintenancebuddy.ui.addVehicle;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.content.Intent;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddVehicleViewModel extends ViewModel {

    // TODO: Implement the ViewModel
    private MutableLiveData<String> mText;

    public AddVehicleViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }
}