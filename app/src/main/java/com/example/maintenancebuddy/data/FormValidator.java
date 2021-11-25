package com.example.maintenancebuddy.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormValidator {

    private final Map<Integer, FieldValidator> fieldValidatorMap;
    private final Map<Integer, List<ValidationListener>> listenerMap;

    public FormValidator() {
        this.fieldValidatorMap = new HashMap<>();
        this.listenerMap = new HashMap<>();
    }

    public void addFieldValidator(int fieldId, FieldValidator fieldValidator) {
        if(fieldValidator == null) return;
        this.fieldValidatorMap.put(fieldId, fieldValidator);
    }

    public void addFieldListener(int fieldId, ValidationListener validationListener) {
        if(validationListener == null) return;
        List<ValidationListener> listeners = this.listenerMap.get(fieldId);
        if(listeners == null) {
            listeners = new ArrayList<>();
            listenerMap.put(fieldId, listeners);
        }
        listeners.add(validationListener);
    }

    public void validate() {
        for(Map.Entry<Integer, FieldValidator> entry : fieldValidatorMap.entrySet()) {
            entry.getValue().validate();
        }
    }

}
