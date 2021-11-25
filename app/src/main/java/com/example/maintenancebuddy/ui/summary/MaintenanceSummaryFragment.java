package com.example.maintenancebuddy.ui.summary;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maintenancebuddy.R;

public class MaintenanceSummaryFragment extends Fragment {

    private MaintenanceSummaryViewModel mViewModel;

    public static MaintenanceSummaryFragment newInstance() {
        return new MaintenanceSummaryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_maintenance_summary, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MaintenanceSummaryViewModel.class);
        // TODO: Use the ViewModel
    }

}