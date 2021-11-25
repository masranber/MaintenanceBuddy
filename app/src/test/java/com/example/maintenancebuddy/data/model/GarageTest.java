package com.example.maintenancebuddy.data.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GarageTest {

    private Garage sut;

    @BeforeEach
    void setUp() {
        sut = new Garage(null, null);
    }

    @AfterEach
    void tearDown() {
        sut = null;
    }

    @Test
    void testSetAndGetUserUID() {
        assertNull(sut.getUserUID());
        sut.setUserUID("uid");
        assertEquals("uid", sut.getUserUID());
    }

    @Test
    void testSetAndGetDescription() {
        assertNull(sut.getDescription());
        sut.setDescription("description");
        assertEquals("description", sut.getDescription());
    }

    @Test
    void testSetAndGetName() {
        assertNull(sut.getName());
        sut.setName("name");
        assertEquals("name", sut.getName());
    }

    @Test
    void testSetAndGetImage() {
        assertNull(sut.getImage());
        sut.setImage("image");
        assertEquals("image", sut.getImage());
    }
}