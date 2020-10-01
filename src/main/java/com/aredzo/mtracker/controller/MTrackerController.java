package com.aredzo.mtracker.controller;


import com.aredzo.mtracker.dto.CarPostRequest;
import com.aredzo.mtracker.dto.CarResponse;
import com.aredzo.mtracker.dto.HousePostRequest;
import com.aredzo.mtracker.dto.HouseResponse;
import com.aredzo.mtracker.dto.MeasurementDto;
import com.aredzo.mtracker.dto.MeterPostRequest;
import com.aredzo.mtracker.dto.MeterResponse;
import com.aredzo.mtracker.service.MTrackerService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping(value = "/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
public class MTrackerController {

    private final MTrackerService mTrackerService;

    public MTrackerController(MTrackerService mTrackerService) {
        this.mTrackerService = mTrackerService;
    }


    @PostMapping("/house")
    public HouseResponse addNewHouse(
            @RequestHeader(name = "authorization") UUID userToken,
            @Valid @RequestBody HousePostRequest request) {
        return mTrackerService.addHouse(request, userToken);
    }

    @GetMapping("/house/{houseId}")
    public HouseResponse getHouse(
            @RequestHeader(name = "authorization") UUID userToken,
            @NotNull @PathVariable int houseId) {
        return mTrackerService.getHouse(houseId, userToken);
    }


    @DeleteMapping("/house/{houseId}")
    public HouseResponse deleteHouse(
            @RequestHeader(name = "authorization") UUID userToken,
            @NotNull @PathVariable int houseId) {
        return mTrackerService.removeHouse(houseId, userToken);
    }


    @PostMapping("/car")
    public CarResponse addNewCar(
            @RequestHeader(name = "authorization") UUID userToken,
            @Valid @RequestBody CarPostRequest request) {
        return mTrackerService.addCar(request, userToken);
    }

    @GetMapping("/car/{carId}")
    public CarResponse getCar(
            @RequestHeader(name = "authorization") UUID userToken,
            @NotNull @PathVariable int carId) {
        return mTrackerService.getCar(carId, userToken);
    }


    @DeleteMapping("/car/{carId}")
    public CarResponse deleteCar(
            @RequestHeader(name = "authorization") UUID userToken,
            @NotNull @PathVariable int carId) {
        return mTrackerService.removeCar(carId, userToken);
    }


    @PostMapping("/car/{carId}/meter")
    public MeterResponse addMeterToCar(
            @RequestHeader(name = "authorization") UUID userToken,
            @NotNull @PathVariable int carId,
            @Valid @RequestBody MeterPostRequest request) {
        return mTrackerService.addMeterToCar(carId, request, userToken);
    }

    @PostMapping("/house/{houseId}/meter")
    public MeterResponse addMeterToHouse(
            @RequestHeader(name = "authorization") UUID userToken,
            @NotNull @PathVariable int houseId,
            @Valid @RequestBody MeterPostRequest request) {
        return mTrackerService.addMeterToHouse(houseId, request, userToken);
    }

    @GetMapping("/meter/{meterId}")
    public MeterResponse getMeter(
            @RequestHeader(name = "authorization") UUID userToken,
            @NotNull @PathVariable int meterId) {
        return mTrackerService.getMeter(meterId, userToken);
    }


    @DeleteMapping("/meter/{meterId}")
    public MeterResponse deleteMeter(
            @RequestHeader(name = "authorization") UUID userToken,
            @NotNull @PathVariable int meterId) {
        return mTrackerService.removeMeter(meterId, userToken);
    }

    @GetMapping("/meter/{meterId}/measurement")
    public List<MeasurementDto> getMeterMeasurements(
            @RequestHeader(name = "authorization") UUID userToken,
            @NotNull @PathVariable int meterId) {
        return mTrackerService.getMeasurementsForMeter(meterId, userToken);
    }

    @PostMapping("/meter/{meterId}/measurement")
    public MeasurementDto addMeasurement(
            @RequestHeader(name = "authorization") UUID userToken,
            @NotNull @PathVariable int meterId,
            @Valid @RequestBody MeasurementDto request) {
        return mTrackerService.addMeasurementToMeter(meterId, request.getValue(), userToken);
    }
}
