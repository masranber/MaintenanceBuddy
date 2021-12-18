package com.example.maintenancebuddy.ui.history;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maintenancebuddy.R;
import com.example.maintenancebuddy.data.DatabaseKeys;
import com.example.maintenancebuddy.data.model.MaintenanceEvent;
import com.example.maintenancebuddy.data.model.Vehicle;
import com.example.maintenancebuddy.databinding.FragmentMaintenanceHistoryBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MaintenanceHistoryFragment extends Fragment {

    private MaintenanceHistoryViewModel       mViewModel;
    private FragmentMaintenanceHistoryBinding binding;

    public static MaintenanceHistoryFragment newInstance() {
        return new MaintenanceHistoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState
    ) {
        mViewModel = new ViewModelProvider(this).get(MaintenanceHistoryViewModel.class);
        binding = FragmentMaintenanceHistoryBinding.inflate(inflater, container, false);
        final View root = binding.getRoot();

        mViewModel.refresh();

        binding.vehicleTitle.setText(mViewModel.getTitleText());

        binding.newRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mViewModel.canAddRecord()) {
                    navigateToNewRecordFragment(view);
                }
            }
        });

        RecyclerView maintenanceEventsRecyclerView = binding.maintenanceHistoryRecyclerView;

        //MaintenanceHistoryRecyclerViewAdapter adapter = (MaintenanceHistoryRecyclerViewAdapter) maintenanceEventsRecyclerView.getAdapter();
        Vehicle currentVehicle = mViewModel.getCurrentVehicle();
        if(currentVehicle != null) {
            binding.newRecordButton.setVisibility(View.VISIBLE);
            binding.emptyHistoryText.setVisibility(View.GONE);
            FirestoreRecyclerOptions<MaintenanceEvent> options = new FirestoreRecyclerOptions.Builder<MaintenanceEvent>()
                    .setQuery(FirebaseFirestore.getInstance()
                            .collection(DatabaseKeys.COLLECTION_RECORDS)
                            .whereEqualTo(DatabaseKeys.FIELD_VEHICLE_UID, currentVehicle.getUID())
                            .orderBy("datePerformed", Query.Direction.DESCENDING), MaintenanceEvent.class)
                    .setLifecycleOwner(getViewLifecycleOwner())
                    .build();
            MaintenanceHistoryRecyclerViewAdapter adapter = new MaintenanceHistoryRecyclerViewAdapter(options);
            maintenanceEventsRecyclerView.setAdapter(adapter);
        } else {
            binding.newRecordButton.setVisibility(View.GONE);
            binding.emptyHistoryText.setVisibility(View.VISIBLE);
        }

        /*mViewModel.observeRecyclerViewOptions(getViewLifecycleOwner(), query -> {
            MaintenanceHistoryRecyclerViewAdapter adapter = (MaintenanceHistoryRecyclerViewAdapter) maintenanceEventsRecyclerView.getAdapter();
            FirestoreRecyclerOptions<MaintenanceEvent> options = new FirestoreRecyclerOptions.Builder<MaintenanceEvent>()
                    .setQuery(query, MaintenanceEvent.class)
                    .setLifecycleOwner(getViewLifecycleOwner())
                    .build();
            if(adapter == null) {
                adapter = new MaintenanceHistoryRecyclerViewAdapter(options);
                maintenanceEventsRecyclerView.setAdapter(adapter);
            } else {
                //adapter.updateOptions(options);
                adapter = new MaintenanceHistoryRecyclerViewAdapter(options);
                maintenanceEventsRecyclerView.setAdapter(adapter);
            }
        });*/

        return root;
    }

    private void navigateToNewRecordFragment(View view) {
        Navigation.findNavController(view).navigate(R.id.action_navigate_to_add_record);
    }

}