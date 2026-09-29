package com.example.carpark;

import com.example.carpark.dto.BillResponse;
import com.example.carpark.dto.ParkResponse;
import com.example.carpark.exception.ApiException;
import com.example.carpark.service.CarParkService;
import com.example.carpark.service.ChargeCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarParkServiceTest {

    private CarParkService newService(int capacity) {
        return new CarParkService(capacity, new ChargeCalculator());
    }

    @Test void parksInFirstFreeSpace() {
        CarParkService svc = newService(3);
        ParkResponse a = svc.park("AA11", 1);
        ParkResponse b = svc.park("BB22", 1);
        assertEquals(1, a.spaceNumber());
        assertEquals(2, b.spaceNumber());
        assertEquals(1, svc.availableSpaces());
    }
    @Test void reusesFreedSpace() {
        CarParkService svc = newService(2);
        svc.park("AA11", 1);
        svc.park("BB22", 1);
        svc.bill("AA11");
        ParkResponse c = svc.park("CC33", 1);
        assertEquals(1, c.spaceNumber());
    }
    @Test void rejectsDuplicateRegistration() {
        CarParkService svc = newService(5);
        svc.park("AA11", 1);
        assertThrows(ApiException.class, () -> svc.park("aa11", 2));
    }
    @Test void rejectsInvalidVehicleType() {
        CarParkService svc = newService(5);
        assertThrows(IllegalArgumentException.class, () -> svc.park("AA11", 7));
    }
    @Test void rejectsBillForUnknownVehicle() {
        CarParkService svc = newService(5);
        assertThrows(ApiException.class, () -> svc.bill("ZZ99"));
    }
    @Test void fullCarParkRejectsNewVehicle() {
        CarParkService svc = newService(1);
        svc.park("AA11", 1);
        assertThrows(ApiException.class, () -> svc.park("BB22", 1));
    }
    @Test void billReturnsChargeAndFreesSpace() {
        CarParkService svc = newService(1);
        svc.park("AA11", 1);
        BillResponse bill = svc.bill("AA11");
        assertNotNull(bill.billId());
        assertEquals("AA11", bill.vehicleReg());
        assertTrue(bill.vehicleCharge() >= 1.10);
        assertEquals(1, svc.availableSpaces());
    }
}
