package com.example.maintenancebuddy.data;

import com.example.maintenancebuddy.data.model.UserAccount;

public interface AuthListener {
    void onSuccess(UserAccount user);
    void onFailure(Exception e);
}
