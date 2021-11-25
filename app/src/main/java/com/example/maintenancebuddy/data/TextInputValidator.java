package com.example.maintenancebuddy.data;

import android.util.Patterns;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class TextInputValidator {

    public static final Pattern EMAIL_ADDRESS
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public static boolean validateRequiredField(String fieldText) {
        return fieldText != null && !fieldText.isEmpty();
    }

    public static boolean validateEmailField(String emailText) {
        return emailText != null && EMAIL_ADDRESS
                        .matcher(emailText)
                        .matches();
    }

    public static boolean validatePasswordField(String passwordText, boolean checkLength) {
        return passwordText != null && !passwordText.isEmpty() && (!checkLength || passwordText.length() >= 8);
    }
}
