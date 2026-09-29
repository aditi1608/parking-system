package com.example.carpark.service;

import com.example.carpark.domain.VehicleType;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ChargeCalculator {

    private static final double FIVE_MIN_BONUS = 1.00;
    private static final int BONUS_INTERVAL_MINUTES = 5;

    public double calculate(VehicleType type, LocalDateTime timeIn, LocalDateTime timeOut) {
        long seconds = Duration.between(timeIn, timeOut).getSeconds();
        if (seconds < 0) {
            throw new IllegalArgumentException("timeOut cannot be before timeIn");
        }
        long minutes = Math.max(1, (long) Math.ceil(seconds / 60.0));
        double perMinuteCharge = minutes * type.getRatePerMinute();
        long bonusBlocks = (long) Math.ceil(minutes / (double) BONUS_INTERVAL_MINUTES);
        double bonusCharge = bonusBlocks * FIVE_MIN_BONUS;
        double total = perMinuteCharge + bonusCharge;
        return Math.round(total * 100.0) / 100.0;
    }
}
