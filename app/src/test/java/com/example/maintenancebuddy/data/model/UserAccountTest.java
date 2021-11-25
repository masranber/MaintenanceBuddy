package com.example.maintenancebuddy.data.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

class UserAccountTest {

    private UserAccount out;

    private static final String expectedEmail = "test@email.com";
    private static final long expectedCreationTimestamp = 1637259481116L;
    private static final long expectedSignInTimestamp = 1637259489739L;

    @BeforeEach
    void setUp() {
        out = new UserAccount("f6d1e646-4897-11ec-81d3-0242ac130003", expectedEmail, expectedCreationTimestamp, expectedSignInTimestamp, "first", "last");
    }

    @AfterEach
    void tearDown() {
        out = null;
    }

    @Test
    void testGetEmailAddress() {
        assertEquals(expectedEmail, out.getEmailAddress());
    }

    @Test
    void testGetCreationTimestamp() {
        assertEquals(expectedCreationTimestamp, out.getCreationTimestamp());
    }

    @Test
    void testGetLastSignInTimestamp() {
        assertEquals(expectedSignInTimestamp, out.getLastSignInTimestamp());
    }
}