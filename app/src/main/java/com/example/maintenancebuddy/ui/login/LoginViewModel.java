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

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class LoginViewModel extends ViewModel {

    public static final int FIELD_EMAIL_ADDR = 1;
    public static final int FIELD_PASSWORD = 2;

    private final UserRepository                            userRepository;
    private final MutableLiveData<UserRepository.AuthState> loginState;
    private final MutableLiveData<String>                   emailError;
    private final MutableLiveData<String> passwordError;

    private final FormValidator formValidator;

    private Disposable authSubscription;

    @Inject
    LoginViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.loginState = new MutableLiveData<>(UserRepository.AuthState.NOT_AUTHENTICATED);
        this.emailError = new MutableLiveData<>();
        this.passwordError = new MutableLiveData<>();
        this.formValidator = new FormValidator();
        attachAuthStateObserver();
    }

    public void login(String email, String password) {
        userRepository.login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
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

    public void validateField(int fieldId, String text) {

    }

    public boolean validateRequiredField(String fieldText) {
        return fieldText != null && !fieldText.isEmpty();
    }

    public boolean validateEmailField(String emailText) {
        return emailText != null && Patterns.EMAIL_ADDRESS.matcher(emailText).matches();
    }

    public boolean validatePasswordField(String passwordText) {
        return passwordText != null && !passwordText.isEmpty();
    }
}