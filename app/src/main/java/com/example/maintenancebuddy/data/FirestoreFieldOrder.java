package com.example.maintenancebuddy.data;

import com.google.firebase.firestore.Query;

public class FirestoreFieldOrder {
    public String          field;
    public Query.Direction order;

    public FirestoreFieldOrder(String field, Query.Direction order) {
        this.field = field;
        this.order = order;
    }
}
