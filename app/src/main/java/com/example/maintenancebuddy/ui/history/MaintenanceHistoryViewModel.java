package com.example.maintenancebuddy.ui.history;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.maintenancebuddy.data.DatabaseKeys;
import com.example.maintenancebuddy.data.FirestoreFieldOrder;
import com.example.maintenancebuddy.data.SortOrder;
import com.example.maintenancebuddy.data.UserRepository;
import com.example.maintenancebuddy.data.model.MaintenanceEvent;
import com.example.maintenancebuddy.data.model.Vehicle;
import com.example.maintenancebuddy.data.repository.VehicleRepository;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.core.OrderBy;

import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MaintenanceHistoryViewModel extends ViewModel implements SnapshotParser<MaintenanceEvent> {
    // TODO: Implement the ViewModel

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final FirebaseFirestore firestore;
    private final MutableLiveData<Query> maintenanceQuery;
    private SortOrder sortOrder;

    @Inject
    public MaintenanceHistoryViewModel(UserRepository userRepository, VehicleRepository vehicleRepository, FirebaseFirestore firestore) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.firestore = firestore;
        this.maintenanceQuery = new MutableLiveData<>();
        this.sortOrder = SortOrder.NEWEST;
    }

    public String getTitleText() {
        Vehicle currentVehicle = vehicleRepository.getCurrentVehicle();
        return currentVehicle == null ? "No Vehicle Selected" : String.format(Locale.US, "%d %s %s", currentVehicle.getYear(), currentVehicle.getMake(), currentVehicle.getModel());
    }

    public boolean canAddRecord() {
        return vehicleRepository.getCurrentVehicle() != null;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
        rebuildQuery();
    }

    public Vehicle getCurrentVehicle() {
        return vehicleRepository.getCurrentVehicle();
    }

    public void refresh() {
        if(vehicleRepository.getCurrentVehicle() != null) rebuildQuery();
    }

    public void observeRecyclerViewOptions(LifecycleOwner lifecycleOwner, Observer<Query> observer) {
        maintenanceQuery.observe(lifecycleOwner, observer);
    }

    public void removeRecyclerViewOptionsObserver(Observer<Query> observer) {
        maintenanceQuery.removeObserver(observer);
    }

    private void rebuildQuery() {
        FirestoreFieldOrder fieldOrder = getFieldOrder(sortOrder);
        Query query = firestore.collection(DatabaseKeys.COLLECTION_RECORDS).whereEqualTo(DatabaseKeys.FIELD_VEHICLE_UID, vehicleRepository.getCurrentVehicle().getUID()).orderBy(fieldOrder.field, fieldOrder.order);
        maintenanceQuery.setValue(query);
    }

    private FirestoreFieldOrder getFieldOrder(SortOrder sortOrder) {
        switch(sortOrder) {
            case NEWEST:
                return new FirestoreFieldOrder(DatabaseKeys.FIELD_MAINTENANCE_DATE_PERFORMED, Query.Direction.DESCENDING);
            case OLDEST:
                return new FirestoreFieldOrder(DatabaseKeys.FIELD_MAINTENANCE_DATE_PERFORMED, Query.Direction.ASCENDING);
            default:
                return new FirestoreFieldOrder(DatabaseKeys.FIELD_MAINTENANCE_DATE_PERFORMED, Query.Direction.DESCENDING);
        }
    }

    @NonNull
    @Override
    public MaintenanceEvent parseSnapshot(@NonNull DocumentSnapshot snapshot) {
        return null;
    }
}