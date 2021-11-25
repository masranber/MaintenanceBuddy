package com.example.maintenancebuddy.ui.transfer;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maintenancebuddy.R;

public class TransferVehicleFragment extends Fragment {

    private TransferVehicleViewModel mViewModel;

    public static TransferVehicleFragment newInstance() {
        return new TransferVehicleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_transfer_vehicle, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TransferVehicleViewModel.class);
        // TODO: Use the ViewModel
    }

}