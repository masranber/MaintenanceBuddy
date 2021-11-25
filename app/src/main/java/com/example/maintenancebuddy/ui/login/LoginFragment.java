package com.example.maintenancebuddy.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.maintenancebuddy.AccountActivity;
import com.example.maintenancebuddy.MainActivity;
import com.example.maintenancebuddy.R;
import com.example.maintenancebuddy.data.AuthListener;
import com.example.maintenancebuddy.data.ValidationListener;
import com.example.maintenancebuddy.data.model.UserAccount;
import com.example.maintenancebuddy.databinding.FragmentLoginBinding;
import com.example.maintenancebuddy.utils.GraphicUtils;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {

    private LoginViewModel       loginViewModel;
    private FragmentLoginBinding binding;

    private CircularProgressButton loginButton;
    private TextInputLayout        emailInput, passwordInput;
    private CircularProgressIndicator loginProgressIndicator;

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            loginButton.revertAnimation();
            navigateToMainActivity();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState
    ) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(this)
                .get(LoginViewModel.class);

        final Button signupButton = binding.loginSignupButton;
        loginButton = binding.loginButton;
        loginProgressIndicator = binding.loginProgressIndicator;

        emailInput = binding.loginEmailAddressInput;
        passwordInput = binding.loginPasswordInput;

        signupButton.setOnClickListener(this::navigateToSignupFragment);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginViewModel.login(emailInput.getEditText().getText().toString(), passwordInput.getEditText().getText().toString());
            }
        });

        loginViewModel.registerFieldValidator(LoginViewModel.FIELD_EMAIL_ADDR, new ValidationListener() {
            @Override
            public void onValidate(boolean isValid, List<Integer> errorCodes) {
                loginButton.setEnabled(isValid);
            }
        });

        loginViewModel.observeLoginState(getViewLifecycleOwner(), loginState -> {
            switch(loginState) {
                case NOT_AUTHENTICATED:
                    loginButton.revertAnimation();
                    break;
                case AUTHENTICATING:
                    loginButton.startAnimation();
                    break;
                case AUTHENTICATED:
                    loginButton.doneLoadingAnimation(getResources().getColor(R.color.colorPrimary), GraphicUtils.rasterizeVectorDrawable(getContext(), R.drawable.ic_baseline_check_circle_outline_24, R.color.white));
                    new Handler().postDelayed(runnable, 500);
                    break;
            }
        });
    }

    private boolean validateTextInputs() {
        if(emailInput == null || passwordInput == null) return false;
        boolean isEmailValid = loginViewModel.validateRequiredField(emailInput.getEditText().getText().toString());
        boolean isPasswordValid = loginViewModel.validateRequiredField(passwordInput.getEditText().getText().toString());

        if(!isEmailValid) {
            emailInput.setError("Required");
        } else {
            emailInput.setErrorEnabled(false);
        }
        if(!isPasswordValid) {
            passwordInput.setError("Required");
        } else {
            passwordInput.setErrorEnabled(false);
        }

        return isEmailValid && isPasswordValid;
    }

    private void navigateToSignupFragment(View view) {
        Navigation.findNavController(view).navigate(R.id.action_navigate_to_signup);
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}