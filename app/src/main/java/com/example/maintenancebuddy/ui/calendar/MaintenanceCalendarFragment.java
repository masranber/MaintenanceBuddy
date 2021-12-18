package com.example.maintenancebuddy.ui.calendar;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maintenancebuddy.data.UserRepository;

import com.example.maintenancebuddy.R;
import com.example.maintenancebuddy.data.model.MaintenanceEvent;
import com.example.maintenancebuddy.data.model.Vehicle;
import com.example.maintenancebuddy.databinding.FragmentLoginBinding;
import com.example.maintenancebuddy.databinding.FragmentMaintenanceCalendarBinding;
import com.example.maintenancebuddy.ui.addVehicle.AddVehicleViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MaintenanceCalendarFragment extends Fragment implements OnCompleteListener<DocumentReference> {

    private MaintenanceCalendarViewModel CalendarViewModel;
    private FragmentMaintenanceCalendarBinding binding;
    FirebaseFirestore db;
    FirebaseAuth auth;
    String userID;

    private ArrayList<MaintenanceEvent> monthlyMaintenance; // list of monthly maintenance
    private ArrayList<MaintenanceEvent> irregularMaintenance; // random maintenance dates set by user

    @Inject
    public UserRepository userRepository;

    public static MaintenanceCalendarFragment newInstance() { return new MaintenanceCalendarFragment();}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        userID = userRepository.getCurrentUser().uid;
        MaintenanceCalendarViewModel CalendarViewModel = new ViewModelProvider(this).get(MaintenanceCalendarViewModel.class);
        binding = FragmentMaintenanceCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        db = FirebaseFirestore.getInstance();

        // retrieves user's vehicles
        db.collection("vehicles").whereEqualTo("userID", userID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) { //for each vehicle
                    //for (int i = 0;  ; i++) {

                }
            } else {
                System.out.println("Error");
            }
        });

        return root;
    }

        @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CalendarViewModel = new ViewModelProvider(this).get(MaintenanceCalendarViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onComplete(@NonNull Task<DocumentReference> task) {
        if(task.isSuccessful()) {
            System.out.println("Success");
        } else {
            String errorMessage = task.getException().getMessage().toLowerCase();
        }
    }
}