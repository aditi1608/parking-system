package com.example.carpark.controller;

import com.example.carpark.dto.*;
import com.example.carpark.service.CarParkService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    private final CarParkService carParkService;

    public ParkingController(CarParkService carParkService) {
        this.carParkService = carParkService;
    }

    @GetMapping
    public SpacesResponse getSpaces() {
        return new SpacesResponse(carParkService.availableSpaces(), carParkService.occupiedSpaces());
    }

    @PostMapping
    public ParkResponse park(@Valid @RequestBody ParkRequest parkRequest) {
        return carParkService.park(parkRequest.getVehicleReg() , parkRequest.getVehicleType());
    }

    @PostMapping("/bill")
    public BillResponse bill(@Valid @RequestBody BillRequest billRequest) {
        return carParkService.bill(billRequest.getVehicleReg());
    }

}
