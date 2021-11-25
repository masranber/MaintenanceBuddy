package com.example.maintenancebuddy.data.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventTest {

    private Event sut;

    @BeforeEach
    void setUp() {
        sut = new Event();
    }

    @AfterEach
    void tearDown() {
        sut = null;
    }

    @Test
    void testSetAndGetReciptURL() {
        assertNull(sut.getReciptURL());
        sut.setReciptURL("testString");
        assertEquals("testString", sut.getReciptURL());
    }

    @Test
    void testSetAndGetCost() {
        assertNull(sut.getCost());
        sut.setCost("testString");
        assertEquals("testString", sut.getCost());
    }

    @Test
    void testSetAndGetPartsList() {
        assertNull(sut.getPartsList());
        sut.setPartsList("testString");
        assertEquals("testString", sut.getPartsList());
    }

    @Test
    void testSetAndGetAdditionDetails() {
        assertNull(sut.getAdditionDetails());
        sut.setAdditionDetails("testString");
        assertEquals("testString", sut.getAdditionDetails());
    }
}