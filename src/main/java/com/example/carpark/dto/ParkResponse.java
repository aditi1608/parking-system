package com.example.carpark.dto;

import java.time.LocalDateTime;

public record ParkResponse(String vehicleReg, int spaceNumber, LocalDateTime timeIn) {}
