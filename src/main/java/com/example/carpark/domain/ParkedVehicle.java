package com.example.carpark.domain;

import java.time.LocalDateTime;

public class ParkedVehicle {
    private final String vehicleReg;
    private final VehicleType vehicleType;
    private final int spaceNumber;
    private final LocalDateTime timeIn;

    public ParkedVehicle(String vehicleReg, VehicleType vehicleType,
                         int spaceNumber, LocalDateTime timeIn) {
        this.vehicleReg = vehicleReg;
        this.vehicleType = vehicleType;
        this.spaceNumber = spaceNumber;
        this.timeIn = timeIn;
    }

    public String getVehicleReg() { return vehicleReg; }
    public VehicleType getVehicleType() { return vehicleType; }
    public int getSpaceNumber() { return spaceNumber; }
    public LocalDateTime getTimeIn() { return timeIn; }
}
