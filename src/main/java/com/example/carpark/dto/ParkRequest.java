package com.example.carpark.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ParkRequest {
    @NotBlank(message = "vehicleReg is required")
    private String vehicleReg;

    @NotNull(message = "vehicleType is required")
    @Min(value = 1, message = "vehicleType must be 1, 2 or 3")
    private Integer vehicleType;

    public String getVehicleReg() { return vehicleReg; }
    public void setVehicleReg(String v) { this.vehicleReg = v; }
    public Integer getVehicleType() { return vehicleType; }
    public void setVehicleType(Integer v) { this.vehicleType = v; }
}
