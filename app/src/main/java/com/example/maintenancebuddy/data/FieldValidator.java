package com.example.maintenancebuddy.data;

import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class FieldValidator {

    private final Map<Integer, Rule> ruleMap;

    public FieldValidator() {
        ruleMap = new HashMap<>();
    }

    public int validate() {
        for(Map.Entry<Integer, Rule> entry : ruleMap.entrySet()) {
            //if(entry.getValue().test())
        }
        return 0;
    }


    public void addRule(int id, Rule rule) {

    }


    public static class Builder {

        private int fieldId;

        public Builder fieldId(int fieldId) {
            this.fieldId = fieldId;
            return this;
        }

        public Builder withRule(Rule rule, ValidationListener listener) {
            return this;
        }

    }

}
