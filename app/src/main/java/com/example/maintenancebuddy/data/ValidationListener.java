package com.example.maintenancebuddy.data;

import java.util.List;

public interface ValidationListener {
    void onValidate(boolean isValid, List<Integer> errorCodes);
}
