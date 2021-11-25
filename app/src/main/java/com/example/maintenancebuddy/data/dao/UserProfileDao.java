package com.example.maintenancebuddy.data.dao;

import com.example.maintenancebuddy.data.DatabaseKeys;
import com.example.maintenancebuddy.data.model.UserAccount;
import com.example.maintenancebuddy.data.model.UserProfile;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.CompletableOnSubscribe;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeEmitter;
import io.reactivex.rxjava3.core.MaybeOnSubscribe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;

public class UserProfileDao {

    private final FirebaseFirestore firestore;

    @Inject
    public UserProfileDao(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    public Completable create(UserProfile userProfile) {
        return Completable.create(emitter -> {
            Task<Void> taskCreate = firestore
                    .collection(DatabaseKeys.COLLECTION_USERS)
                    .document(userProfile.uid)
                    .set(userProfile);
            try {
                Tasks.await(taskCreate);
                if(!emitter.isDisposed()) emitter.onComplete();
            } catch (Exception e) {
                if(!emitter.isDisposed()) emitter.onError(e);
            }
        });
    }

    public Single<UserProfile> getWithUID(String uid) {
        return Single.create(emitter -> {
            Task<DocumentSnapshot> taskCreate = firestore.collection(DatabaseKeys.COLLECTION_USERS).document(uid).get();
            try {
                DocumentSnapshot userSnapshot = Tasks.await(taskCreate);
                UserProfile userProfile = userSnapshot.toObject(UserProfile.class);
                if(userProfile != null) {
                    userProfile.uid = userSnapshot.getId();
                    if(!emitter.isDisposed()) emitter.onSuccess(userProfile);
                } else {
                    if(!emitter.isDisposed()) emitter.onError(new FileNotFoundException("User profile not found in database"));
                }

            } catch (Exception e) {
                if(!emitter.isDisposed()) emitter.onError(e);
            }
        });
    }

    //public void update(UserAccount userAccount, Map<String, Object> updates) {

    //}
}
