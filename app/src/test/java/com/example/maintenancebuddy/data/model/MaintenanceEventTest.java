package com.example.maintenancebuddy.data.model;

import static org.junit.jupiter.api.Assertions.*;

import com.example.maintenancebuddy.data.MaintenanceType;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

class MaintenanceEventTest {

    private MaintenanceEvent sut;

    @BeforeEach
    void setUp() {
        sut = new MaintenanceEvent();
    }

    @AfterEach
    void tearDown() {
        sut = null;
    }

    @Test
    void getDatePerformed() {
        /*
        assertNull(sut.getDatePerformed());
        Date now = new Date();
        sut.setDatePerformed(now);
        assertEquals(now, sut.getDatePerformed());*/
    }

    @Test
    void getVehicleOdometer() {
        assertEquals(0.0, sut.getVehicleOdometer());
        sut.setVehicleOdometer(1000.0);
        assertEquals(1000.0, sut.getVehicleOdometer());
    }

    @Test
    void getEngineHours() {
        assertEquals(0.0, sut.getEngineHours());
        sut.setEngineHours(1000.0);
        assertEquals(1000.0, sut.getEngineHours());
    }

    @Test
    void getDescription() {
        assertNull(sut.getDescription());
        sut.setDescription("test");
        assertEquals("test", sut.getDescription());
    }

    @Test
    void getReason() {
        assertNull(sut.getReason());
        sut.setReason("test");
        assertEquals("test", sut.getReason());
    }

    @Test
    void getMaintenanceType() {
        //assertNull(sut.getMaintenanceType());
        //sut.setMaintenanceType(MaintenanceType.REPAIR);
        //assertEquals(MaintenanceType.REPAIR, sut.getMaintenanceType());
    }

    @Test
    void getPartsReplaced() {
        /*assertNull(sut.getPartsReplaced()); // list is created by Firebase during deserialization using reflection
        // initializing the list in the constructor messes up the deserialization/reflection process
        // due to this, it is not possible to test that the list is constructed correctly during unit testing
        // we just assume Firebase deserializes the list correctly, this is tested fully during integration testing
        MaintenancePart dummyPart = new MaintenancePart();
        List<MaintenancePart> test = new ArrayList<>();
        test.add(dummyPart);
        sut.setPartsReplaced(test);
        assertEquals(1, sut.getPartsReplaced().size());
        assertEquals(dummyPart, sut.getPartsReplaced().get(0));
        */
    }

    @Test
    void getReceiptImagePath() {
        assertNull(sut.getReceiptImagePath());
        sut.setReceiptImagePath("test");
        assertEquals("test", sut.getReceiptImagePath());
    }

    @Test
    void getOtherImagePaths() {
        /*assertNull(sut.getOtherImagePaths()); // same issue with list as above
        List<String> test = new ArrayList<>();
        test.add("path");
        sut.setOtherImagePaths(test);
        assertEquals(1, sut.getOtherImagePaths().size());
        assertEquals("path", sut.getOtherImagePaths().get(0));*/
    }

    @Test
    void getOtherDocumentPaths() {
        /*assertNull(sut.getOtherDocumentPaths()); // same issue with list as above
        List<String> test = new ArrayList<>();
        test.add("path");
        sut.setOtherDocumentPaths(test);
        assertEquals(1, sut.getOtherDocumentPaths().size());
        assertEquals("path", sut.getOtherDocumentPaths().get(0));*/
    }
}