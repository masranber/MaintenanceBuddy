package com.example.maintenancebuddy.ui.garage;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maintenancebuddy.R;
import com.example.maintenancebuddy.data.DatabaseKeys;
import com.example.maintenancebuddy.data.UserRepository;
import com.example.maintenancebuddy.data.dao.GarageDao;
import com.example.maintenancebuddy.data.model.Garage;
import com.example.maintenancebuddy.data.model.Vehicle;
import com.example.maintenancebuddy.data.repository.VehicleRepository;
import com.example.maintenancebuddy.databinding.FragmentDashboardBinding;
import com.example.maintenancebuddy.databinding.FragmentGarageBinding;
import com.example.maintenancebuddy.ui.recyclerview.OnItemClickListener;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class GarageFragment extends Fragment {

    private GarageViewModel mViewModel;
    private FragmentGarageBinding binding;

    @Inject
    protected UserRepository userRepository;

    @Inject
    protected GarageDao garageDao;

    @Inject
    protected VehicleRepository vehicleRepository;

    public static GarageFragment newInstance() {
        return new GarageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(GarageViewModel.class);

        binding = FragmentGarageBinding.inflate(inflater, container, false);

        RecyclerView vehiclesRecyclerView = binding.vehiclesRecyclerView;
        vehiclesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        String userUID = userRepository.getCurrentUser().uid;

        garageDao.getWithUID(userUID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<Garage, Throwable>() {
                    @Override
                    public void accept(Garage garage, Throwable throwable) throws Throwable {
                        if(throwable != null) {
                            Log.i("GarageFragment", throwable.getMessage());
                        } else {
                            binding.garageTitle.setText(garage.getName());
                        }
                    }
                });

        FirestoreRecyclerOptions<Vehicle> options = new FirestoreRecyclerOptions.Builder<Vehicle>()
                .setLifecycleOwner(getViewLifecycleOwner())
                .setQuery(FirebaseFirestore.getInstance().collection(DatabaseKeys.COLLECTION_VEHICLES).whereEqualTo(DatabaseKeys.FIELD_VEHICLE_GARAGE_UID, userUID), Vehicle.class)
                .build();

        GarageRecyclerViewAdapter adapter = new GarageRecyclerViewAdapter(options);
        vehiclesRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener<Vehicle>() {
            @Override
            public void onItemClick(Vehicle item) {
                vehicleRepository.setCurrentVehicle(item);
                navigateToVehicleHistoryFragment(binding.getRoot());
            }
        });

        binding.addVehicleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToAddVehicleFragment(view);
            }
        });

        return binding.getRoot();
    }

    private void navigateToVehicleHistoryFragment(View view) {
        Navigation.findNavController(view).navigate(R.id.action_navigate_to_vehicle_history);
    }

    private void navigateToAddVehicleFragment(View view) {
        Navigation.findNavController(view).navigate(R.id.action_navigate_to_add_vehicle);
    }

}