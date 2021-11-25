package com.example.maintenancebuddy;

import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.maintenancebuddy.data.FormValidator;
import com.example.maintenancebuddy.data.UserRepository;
import com.example.maintenancebuddy.data.ValidationListener;
import com.example.maintenancebuddy.data.dao.GarageDao;
import com.example.maintenancebuddy.data.model.Garage;
import com.example.maintenancebuddy.data.model.UserProfile;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class MainActivityViewModel extends ViewModel {

    private final UserRepository userRepository;
    private final     GarageDao      garageDao;

    @Inject
    MainActivityViewModel(UserRepository userRepository, GarageDao garageDao) {
        this.userRepository = userRepository;
        this.garageDao = garageDao;
        Log.i("UserRepository", userRepository.toString());
    }


    public void createDefaultGarage() {
        UserProfile currentUser = userRepository.getCurrentUser();
        String garageName = String.format("%s's Garage", currentUser.firstName);
        Garage defaultGarage = new Garage(currentUser.uid, garageName);
        defaultGarage.setUID(currentUser.uid);
        garageDao.create(defaultGarage)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}