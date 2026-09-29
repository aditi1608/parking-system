package com.example.carpark.domain;

import java.util.Arrays;

public enum VehicleType {
    SMALL(1, 0.10),
    MEDIUM(2, 0.20),
    LARGE(3, 0.40);

    private final int code;
    private final double ratePerMinute;

    VehicleType(int code, double ratePerMinute) {
        this.code = code;
        this.ratePerMinute = ratePerMinute;
    }

    public int getCode() { return code; }
    public double getRatePerMinute() { return ratePerMinute; }

    public static VehicleType fromCode(int code) {
        return Arrays.stream(values())
                .filter(t -> t.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid vehicle type: " + code + " (must be 1, 2 or 3)"));
    }
}
