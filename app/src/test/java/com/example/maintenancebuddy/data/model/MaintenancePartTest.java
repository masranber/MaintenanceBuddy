package com.example.maintenancebuddy.data.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MaintenancePartTest {

    private MaintenancePart sut;

    @BeforeEach
    void setUp() {
        sut = new MaintenancePart();
    }

    @AfterEach
    void tearDown() {
        sut = null;
    }

    @Test
    void getName() {
        assertNull(sut.getName());
        sut.setName("test");
        assertEquals("test", sut.getName());
    }

    @Test
    void getPartNumber() {
        assertNull(sut.getPartNumber());
        sut.setPartNumber("test");
        assertEquals("test", sut.getPartNumber());
    }

    @Test
    void getBrand() {
        assertNull(sut.getBrand());
        sut.setBrand("test");
        assertEquals("test", sut.getBrand());
    }

    @Test
    void getPurchaseLocation() {
        assertNull(sut.getPurchaseLocation());
        sut.setPurchaseLocation("test");
        assertEquals("test", sut.getPurchaseLocation());
    }

    @Test
    void getPrice() {
        assertEquals(0.0, sut.getPrice());
        sut.setPrice(20.0);
        assertEquals(20.0, sut.getPrice());
    }
}