package com.example.maintenancebuddy.data.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VehicleTest {

    private Vehicle sut;

    @BeforeEach
    void setUp() {
        sut = new Vehicle(null, null, null, 0);
    }

    @AfterEach
    void tearDown() {
        sut = null;
    }

    @Test
    public void testConstructorParameters() {
        sut = new Vehicle("userID", "make", "model", 2021);
        assertEquals("userID", sut.getUserUID());
        assertEquals("make", sut.getMake());
        assertEquals("model", sut.getModel());
        assertEquals(2021, sut.getYear());
    }

    @Test
    void testSetAndGetUserID() {
        assertNull(sut.getUserUID());
        sut.setUserUID("uid");
        assertEquals("uid", sut.getUserUID());
    }

    @Test
    void testSetAndGetGarageID() {
        assertNull(sut.getGarageUID());
        sut.setGarageUID("uid");
        assertEquals("uid", sut.getGarageUID());
    }

    @Test
    void testSetAndGetUID() {
        assertNull(sut.getUID());
        sut.setUID("uid");
        assertEquals("uid", sut.getUID());
    }

    @Test
    void testSetAndGetMake() {
        assertNull(sut.getMake());
        sut.setMake("testString");
        assertEquals("testString", sut.getMake());
    }

    @Test
    void testSetAndGetMiles() {
        //assertNull(sut.getMiles());
        sut.setMiles(100000);
        assertEquals(100000, sut.getMiles());
    }

    @Test
    void testSetAndGetYear() {
        //assertNull(sut.getYear());
        sut.setYear(2000);
        assertEquals(2000, sut.getYear());
    }

    @Test
    void testSetAndGetModel() {
        assertNull(sut.getModel());
        sut.setModel("testString");
        assertEquals("testString", sut.getModel());
    }

    @Test
    void testSetAndGetColor() {
        assertNull(sut.getColor());
        sut.setColor("testString");
        assertEquals("testString", sut.getColor());
    }

    @Test
    void testSetAndGetVin() {
        assertNull(sut.getVin());
        sut.setVin("testString");
        assertEquals("testString", sut.getVin());
    }

    @Test
    void testSetAndGetPictureURL() {
        assertNull(sut.getPictureURL());
        sut.setPictureURL("testString");
        assertEquals("testString", sut.getPictureURL());
    }
}