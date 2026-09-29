package com.example.carpark.service;

import com.example.carpark.domain.ParkedVehicle;
import com.example.carpark.domain.VehicleType;
import com.example.carpark.dto.BillResponse;
import com.example.carpark.dto.ParkResponse;
import com.example.carpark.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CarParkService {

    private final int capacity;
    private final ChargeCalculator chargeCalculator;

    public CarParkService(@Value("${carpark.capacity:100}") int capacity,
                          ChargeCalculator chargeCalculator) {
        this.capacity = capacity;
        this.chargeCalculator = chargeCalculator;
    }

    public synchronized int availableSpaces() { return capacity - spaces.size(); }
    public synchronized int occupiedSpaces() { return spaces.size(); }

    private final Map<String, Integer> vehicleMap = new ConcurrentHashMap<>();
    private final Map<Integer, ParkedVehicle> spaces = new ConcurrentHashMap<>();



    public synchronized ParkResponse park(String vehicleReg, int vehicleTypeCode) {
        String standardVehicleReg = vehicleReg.trim().toUpperCase();

        if (vehicleMap.containsKey(standardVehicleReg)) {
            throw new ApiException(HttpStatus.CONFLICT,
                    "Vehicle " + standardVehicleReg + " is already parked");
        }

        VehicleType type = VehicleType.fromCode(vehicleTypeCode);
        int spaceNumber = findFirstFreeSpace();

        ParkedVehicle vehicle = new ParkedVehicle(
                standardVehicleReg, type, spaceNumber, LocalDateTime.now());
        spaces.put(spaceNumber, vehicle);
        vehicleMap.put(standardVehicleReg, spaceNumber);

        return new ParkResponse(standardVehicleReg, spaceNumber, vehicle.getTimeIn());
    }


    public synchronized BillResponse bill(String reg) {
        String standardVehicleReg = reg.trim().toUpperCase();

        Integer spaceNumber = vehicleMap.get(standardVehicleReg);
        if (spaceNumber == null) {
            throw new ApiException(HttpStatus.NOT_FOUND,
                    "Vehicle " + standardVehicleReg + " is not currently parked");
        }

        ParkedVehicle vehicle = spaces.remove(spaceNumber);
        vehicleMap.remove(standardVehicleReg);

        LocalDateTime timeOut = LocalDateTime.now();
        double charge = chargeCalculator.calculate(
                vehicle.getVehicleType(), vehicle.getTimeIn(), timeOut);

        return new BillResponse(
                UUID.randomUUID().toString(),
                standardVehicleReg,
                charge,
                vehicle.getTimeIn(),
                timeOut);
    }


    private int findFirstFreeSpace() {
        for (int i = 1; i <= capacity; i++) {
            if (!spaces.containsKey(i)) return i;
        }
        throw new ApiException(HttpStatus.CONFLICT, "Car park is full");
    }
}
