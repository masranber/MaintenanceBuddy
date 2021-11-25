package com.example.maintenancebuddy.data.dao;

import com.example.maintenancebuddy.data.DatabaseKeys;
import com.example.maintenancebuddy.data.model.Garage;
import com.example.maintenancebuddy.data.model.UserProfile;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class GarageDao {

    private final FirebaseFirestore firestore;

    @Inject
    public GarageDao(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    public Completable create(Garage garage) {
        return Completable.create(emitter -> {
            Task<Void> taskCreate = firestore
                    .collection(DatabaseKeys.COLLECTION_GARAGES)
                    .document(garage.getUID())
                    .set(garage);
            try {
                Tasks.await(taskCreate);
                if(!emitter.isDisposed()) emitter.onComplete();
            } catch (Exception e) {
                if(!emitter.isDisposed()) emitter.onError(e);
            }
        });
    }

    public Single<Garage> getWithUID(String uid) {
        return Single.create(emitter -> {
            Task<DocumentSnapshot> taskCreate = firestore.collection(DatabaseKeys.COLLECTION_GARAGES).document(uid).get();
            try {
                DocumentSnapshot userSnapshot = Tasks.await(taskCreate);
                Garage garage = userSnapshot.toObject(Garage.class);
                if(garage != null) {
                    if(!emitter.isDisposed()) emitter.onSuccess(garage);
                } else {
                    if(!emitter.isDisposed()) emitter.onError(new FileNotFoundException("User profile not found in database"));
                }

            } catch (Exception e) {
                if(!emitter.isDisposed()) emitter.onError(e);
            }
        });
    }
}
