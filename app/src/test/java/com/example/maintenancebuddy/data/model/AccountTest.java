package com.example.maintenancebuddy.data.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountTest {

    private Account out; // object under test (out)

    private static final String expectedUid = "f6d1e646-4897-11ec-81d3-0242ac130003";
    private static final String expectedFirstName = "first";
    private static final String expectedLastName = "last";
    private static final String expectedFullName = "first last";

    @BeforeEach
    void setUp() {
        out = new Account(expectedUid, expectedFirstName, expectedLastName);
    }

    @AfterEach
    void tearDown() {
        out = null;
    }

    @Test
    public void testGetUid() {
        assertEquals(expectedUid, out.getUID());
    }

    @Test
    public void testGetFirstName() {
        assertEquals(expectedFirstName, out.getFirstName());
    }

    @Test
    public void testGetLastName() {
        assertEquals(expectedLastName, out.getLastName());
    }

    @Test
    public void testGetFullName() {
        assertEquals(expectedFullName, out.getFullName());
    }

    @Test
    public void testEqualsOnAllFieldsEqual() {
        Account equal = new Account(expectedUid, expectedFirstName, expectedLastName);
        assertEquals(equal, out); // assertEquals calls equalTo method implicitly
    }

    @Test
    public void testEqualsOnNameNotEqual() {
        Account equal = new Account(expectedUid, "first2", "last2");
        assertEquals(equal, out);
    }

    @Test
    public void testNotEqualsOnUidNotEqual() {
        Account notEqual = new Account("different_uid", expectedLastName, expectedLastName);
        assertNotEquals(notEqual, out);
    }
}