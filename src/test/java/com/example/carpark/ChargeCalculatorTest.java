package com.example.carpark;

import com.example.carpark.domain.VehicleType;
import com.example.carpark.service.ChargeCalculator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChargeCalculatorTest {

    private final ChargeCalculator calc = new ChargeCalculator();
    private final LocalDateTime t0 = LocalDateTime.of(2024, 1, 1, 12, 0);

    @Test void smallCarOneMinute() {
        assertEquals(1.10, calc.calculate(VehicleType.SMALL, t0, t0.plusMinutes(1)), 0.001);
    }
    @Test void smallCarExactlyFiveMinutes() {
        assertEquals(1.50, calc.calculate(VehicleType.SMALL, t0, t0.plusMinutes(5)), 0.001);
    }
    @Test void mediumCarSixMinutes() {
        assertEquals(3.20, calc.calculate(VehicleType.MEDIUM, t0, t0.plusMinutes(6)), 0.001);
    }
    @Test void largeCarTenMinutes() {
        assertEquals(6.00, calc.calculate(VehicleType.LARGE, t0, t0.plusMinutes(10)), 0.001);
    }
    @Test void partMinuteRoundsUp() {
        assertEquals(1.20, calc.calculate(VehicleType.SMALL, t0, t0.plusSeconds(61)), 0.001);
    }
}
