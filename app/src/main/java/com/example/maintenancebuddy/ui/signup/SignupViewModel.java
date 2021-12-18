package com.example.maintenancebuddy.ui.signup;

import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.maintenancebuddy.data.AuthListener;
import com.example.maintenancebuddy.data.DatabaseKeys;
import com.example.maintenancebuddy.data.TextInputValidator;
import com.example.maintenancebuddy.data.UserRepository;
import com.example.maintenancebuddy.data.model.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class SignupViewModel extends ViewModel {

    public enum SignupResult {
        FIRST_NAME_REQ, LAST_NAME_REQ, EMAIL_REQ, PASS_REQ, PASS_TOO_SHORT, EMAIL_INVALID, EMAIL_ALREADY_EXISTS, UNKNOWN_ERROR, NO_INTERNET, SUCCESS;
    }

    public enum SignupState {
        IDLE, BUSY;
    }

    private final MutableLiveData<SignupResult> signupResult;
    private final MutableLiveData<SignupState> signupState;
    private final UserRepository userRepository;

    @Inject
    public SignupViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.signupState = new MutableLiveData<>(SignupState.IDLE);
        this.signupResult = new MutableLiveData<>();
    }

    public void observeSignupState(LifecycleOwner lifecycleOwner, Observer<? super SignupState> observer) {
        signupState.observe(lifecycleOwner, observer);
    }

    public void observeSignupResult(LifecycleOwner lifecycleOwner, Observer<? super SignupResult> observer) {
        signupResult.observe(lifecycleOwner, observer);
    }

    public void createUser(String firstName, String lastName, String emailAddress, String password) {
        if(!TextInputValidator.validateRequiredField(firstName)) {
            signupResult.setValue(SignupResult.FIRST_NAME_REQ);
            return;
        } else if(!TextInputValidator.validateRequiredField(lastName)) {
            signupResult.setValue(SignupResult.LAST_NAME_REQ);
            return;
        } else if(!TextInputValidator.validateRequiredField(emailAddress)) {
            signupResult.setValue(SignupResult.EMAIL_REQ);
            return;
        } else if(!TextInputValidator.validateRequiredField(password)) {
            signupResult.setValue(SignupResult.PASS_REQ);
            return;
        } else if(!TextInputValidator.validateEmailField(emailAddress)) {
            signupResult.setValue(SignupResult.EMAIL_INVALID);
            return;
        } else if(!TextInputValidator.validatePasswordField(password, true)) {
            signupResult.setValue(SignupResult.PASS_TOO_SHORT);
            return;
        }
        userRepository.createUser(firstName, lastName, emailAddress, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        signupState.postValue(SignupState.BUSY);
                    }

                    @Override
                    public void onComplete() {
                        signupState.postValue(SignupState.IDLE);
                        signupResult.postValue(SignupResult.SUCCESS);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        signupState.postValue(SignupState.IDLE);
                        if(e.getCause() != null) {
                            e = e.getCause();
                            if(e instanceof FirebaseAuthUserCollisionException) {
                                signupResult.postValue(SignupResult.EMAIL_ALREADY_EXISTS);
                            } else if(e instanceof FirebaseNetworkException) {
                                signupResult.postValue(SignupResult.NO_INTERNET);
                            } else {
                                signupResult.postValue(SignupResult.UNKNOWN_ERROR);
                            }
                        } else {
                            signupResult.postValue(SignupResult.UNKNOWN_ERROR);
                        }
                    }
                });
    }
}