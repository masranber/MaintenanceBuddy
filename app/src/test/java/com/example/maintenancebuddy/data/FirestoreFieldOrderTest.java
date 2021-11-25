package com.example.maintenancebuddy.data;

import static org.junit.jupiter.api.Assertions.*;

import com.google.firebase.firestore.Query;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FirestoreFieldOrderTest {

    @Test
    void testConstructor() {
        FirestoreFieldOrder sut = new FirestoreFieldOrder("field", Query.Direction.ASCENDING);
        assertEquals("field", sut.field);
        assertEquals(Query.Direction.ASCENDING, sut.order);
    }
}