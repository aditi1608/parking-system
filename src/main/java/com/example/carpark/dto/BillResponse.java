package com.example.carpark.dto;

import java.time.LocalDateTime;

public record BillResponse(
        String billId,
        String vehicleReg,
        double vehicleCharge,
        LocalDateTime timeIn,
        LocalDateTime timeOut) {}
