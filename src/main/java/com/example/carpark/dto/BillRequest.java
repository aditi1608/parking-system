package com.example.carpark.dto;

import jakarta.validation.constraints.NotBlank;

public class BillRequest {
    @NotBlank(message = "vehicleReg is required")
    private String vehicleReg;

    public String getVehicleReg() { return vehicleReg; }
    public void setVehicleReg(String v) { this.vehicleReg = v; }
}
