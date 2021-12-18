package com.example.maintenancebuddy.ui.signup;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.maintenancebuddy.R;
import com.example.maintenancebuddy.data.AuthListener;
import com.example.maintenancebuddy.data.DatabaseKeys;
import com.example.maintenancebuddy.data.TextInputValidator;
import com.example.maintenancebuddy.data.model.UserAccount;
import com.example.maintenancebuddy.databinding.FragmentSignupBinding;
import com.example.maintenancebuddy.utils.GraphicUtils;
import com.google.android.material.textfield.TextInputLayout;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignupFragment extends Fragment {

    private static final String LOCAL_FIELD_USERS_EMAIL = "localEmail";

    private SignupViewModel mViewModel;
    private FragmentSignupBinding binding;

    private TextInputLayout firstNameInput, lastNameInput, emailInput, passwordInput;
    private CircularProgressButton signupButton;

    public static SignupFragment newInstance() {
        return new SignupFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(SignupViewModel.class);

        binding = FragmentSignupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        firstNameInput          = binding.signupFirstNameInput;
        lastNameInput           = binding.signupLastNameInput;
        emailInput              = binding.signupEmailAddressInput;
        passwordInput           = binding.signupPasswordInput;

        // Load saved text input states on resuming from a configuration change
        if(savedInstanceState != null) {
            String savedFirstName =   savedInstanceState.getString(DatabaseKeys.FIELD_USERS_FIRST_NAME);
            String savedLastName =    savedInstanceState.getString(DatabaseKeys.FIELD_USERS_LAST_NAME);
            String savedEmail =       savedInstanceState.getString(LOCAL_FIELD_USERS_EMAIL);
            if(savedFirstName != null)   firstNameInput.getEditText().setText(savedFirstName);
            if(savedLastName != null)    lastNameInput.getEditText().setText(savedLastName);
            if(savedEmail != null)       emailInput.getEditText().setText(savedEmail);
        }

        // Set up navigation action back to login screen
        Button loginButton = binding.signupLoginButton;
        loginButton.setOnClickListener(this::navigateToLoginFragment);

        signupButton = binding.signupButton;
        signupButton.setOnClickListener(view -> {
            signupButton.startAnimation();
            mViewModel.createUser(
                    firstNameInput.getEditText().getText().toString(),
                    lastNameInput.getEditText().getText().toString(),
                    emailInput.getEditText().getText().toString(),
                    passwordInput.getEditText().getText().toString()
            );
        });

        mViewModel.observeSignupState(getViewLifecycleOwner(), signupState -> {
            if (signupState == SignupViewModel.SignupState.BUSY) {
                signupButton.startAnimation();
            } else {
                signupButton.revertAnimation();
            }
        });

        mViewModel.observeSignupResult(getViewLifecycleOwner(), signupResult -> {
            firstNameInput.setErrorEnabled(false);
            lastNameInput.setErrorEnabled(false);
            emailInput.setErrorEnabled(false);
            passwordInput.setErrorEnabled(false);
            setErrorMessage(null);
            signupButton.revertAnimation();
            switch(signupResult) {
                case SUCCESS:
                    signupButton.doneLoadingAnimation(getResources().getColor(R.color.colorPrimary), GraphicUtils.rasterizeVectorDrawable(getContext(), R.drawable.ic_baseline_check_circle_outline_24, R.color.white));
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            navigateToLoginFragment(root);
                        }
                    };
                    new Handler().postDelayed(runnable, 500);
                    break;
                case FIRST_NAME_REQ:
                    firstNameInput.setError("Required *");
                    break;
                case LAST_NAME_REQ:
                    lastNameInput.setError("Required *");
                    break;
                case EMAIL_REQ:
                    emailInput.setError("Required *");
                    break;
                case PASS_REQ:
                    passwordInput.setError("Required *");
                    break;
                case PASS_TOO_SHORT:
                    passwordInput.setError("Must be at least 8 characters");
                    break;
                case EMAIL_INVALID:
                    emailInput.setError("Invalid email address");
                    break;
                case EMAIL_ALREADY_EXISTS:
                    setErrorMessage("An account with this email address already exists.");
                    break;
                case NO_INTERNET:
                    setErrorMessage("There was a problem connecting. Please check your internet connection and try again.");
                    break;
                case UNKNOWN_ERROR:
                default:
                    setErrorMessage("An unknown error occurred. Please try again later.");
                    break;

            }
        });

        return root;
    }

    private boolean validateTextInputs() {
        boolean isEmailValid = TextInputValidator.validateEmailField(emailInput.getEditText().getText().toString());
        boolean isPasswordValid = TextInputValidator.validatePasswordField(passwordInput.getEditText().getText().toString(), true);
        boolean isFirstNameValid = TextInputValidator.validateRequiredField(firstNameInput.getEditText().getText().toString());
        boolean isLastNameValid = TextInputValidator.validateRequiredField(lastNameInput.getEditText().getText().toString());

        return isFirstNameValid && isLastNameValid && isEmailValid && isPasswordValid;
    }

    private void navigateToLoginFragment(View view) {
        Navigation.findNavController(view).navigate(R.id.action_navigate_to_login);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(firstNameInput != null && firstNameInput.getEditText() != null) {
            outState.putString(DatabaseKeys.FIELD_USERS_FIRST_NAME, firstNameInput.getEditText().getText().toString());
            outState.putString(DatabaseKeys.FIELD_USERS_LAST_NAME, lastNameInput.getEditText().getText().toString());
            outState.putString(LOCAL_FIELD_USERS_EMAIL, emailInput.getEditText().getText().toString());
        }
    }

    private void setErrorMessage(String errorMessage) {
        binding.signupErrorText.setVisibility(errorMessage == null ? View.GONE : View.VISIBLE);
        if(errorMessage == null) return;
        binding.signupErrorText.setText(errorMessage);
    }
}