package com.example.maintenancebuddy.ui.login;

import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.maintenancebuddy.data.AuthListener;
import com.example.maintenancebuddy.data.FormValidator;
import com.example.maintenancebuddy.data.UserRepository;
import com.example.maintenancebuddy.data.ValidationListener;
import com.example.maintenancebuddy.data.model.UserAccount;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class LoginViewModel extends ViewModel {

    public static final int FIELD_EMAIL_ADDR = 1;
    public static final int FIELD_PASSWORD = 2;

    private final UserRepository                            userRepository;
    private final MutableLiveData<UserRepository.AuthState> loginState;
    private final MutableLiveData<String>                   emailError;
    private final MutableLiveData<String> error;

    private final FormValidator formValidator;

    private Disposable authSubscription;

    @Inject
    LoginViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.loginState = new MutableLiveData<>(UserRepository.AuthState.NOT_AUTHENTICATED);
        this.emailError = new MutableLiveData<>();
        this.error = new MutableLiveData<>();
        this.formValidator = new FormValidator();
        attachAuthStateObserver();
    }

    public void login(String email, String password) {
        userRepository.login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    error.postValue(null); // no errors
                }, throwable -> {
                    if(throwable.getCause() != null) {
                        throwable = throwable.getCause();
                        if(throwable instanceof FirebaseAuthInvalidCredentialsException ||
                                throwable instanceof FirebaseAuthInvalidUserException) {
                            error.postValue("Invalid email or password.");
                        } else if(throwable instanceof FirebaseNetworkException) {
                            error.postValue("There was a problem connecting. Please check your internet connection and try again.");
                        } else {
                            error.postValue("An unknown error occurred. Please try again later.");
                        }
                    } else {
                        error.postValue(throwable.getMessage());
                    }
                });
    }

    public void logout() {
        userRepository.logout();
    }

    public void observeLoginState(LifecycleOwner lifecycleOwner, Observer<? super UserRepository.AuthState> observer) {
        loginState.observe(lifecycleOwner, observer);
    }

    public void registerFieldValidator(int fieldId, ValidationListener validationListener) {

    }

    private void attachAuthStateObserver() {
        authSubscription = userRepository.getAuthStateObservable()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginState::postValue);
    }

    private void removeAuthStateObserver() {
        authSubscription.dispose();
    }

    public void observeError(LifecycleOwner lifecycleOwner, Observer<String> observer) {
        error.observe(lifecycleOwner, observer);
    }
}