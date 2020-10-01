package com.aredzo.mtracker.service;

import com.aredzo.mtracker.dto.CarPostRequest;
import com.aredzo.mtracker.dto.CarResponse;
import com.aredzo.mtracker.dto.HousePostRequest;
import com.aredzo.mtracker.dto.HouseResponse;
import com.aredzo.mtracker.dto.MeasurementDto;
import com.aredzo.mtracker.dto.MeterPostRequest;
import com.aredzo.mtracker.dto.MeterResponse;
import com.aredzo.mtracker.dto.mapper.MTrackerMapper;
import com.aredzo.mtracker.entity.CarEntity;
import com.aredzo.mtracker.entity.HouseEntity;
import com.aredzo.mtracker.entity.MeasurementEntity;
import com.aredzo.mtracker.entity.MeterEntity;
import com.aredzo.mtracker.exception.MTrackerServiceError;
import com.aredzo.mtracker.exception.MTrackerServiceException;
import com.aredzo.mtracker.repository.CarRepository;
import com.aredzo.mtracker.repository.HouseRepository;
import com.aredzo.mtracker.repository.MeasurementRepository;
import com.aredzo.mtracker.repository.MeterRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MTrackerService {


    private final CarRepository carRepository;
    private final HouseRepository houseRepository;
    private final MeasurementRepository measurementRepository;
    private final MeterRepository meterRepository;
    private final MTrackerMapper mapper;
    private final SsoClientService ssoClientService;

    public MTrackerService(CarRepository carRepository,
                           HouseRepository houseRepository,
                           MeasurementRepository measurementRepository,
                           MeterRepository meterRepository,
                           SsoClientService ssoClientService) {

        this.carRepository = carRepository;
        this.houseRepository = houseRepository;
        this.measurementRepository = measurementRepository;
        this.meterRepository = meterRepository;
        this.ssoClientService = ssoClientService;
        mapper = Mappers.getMapper(MTrackerMapper.class);
    }


    public HouseResponse addHouse(HousePostRequest request, UUID token) {
        return mapper.mapHouseEntityToResponse(
                houseRepository.save(
                        new HouseEntity(
                                request.getHouseName(),
                                request.getDescription(),
                                ssoClientService.validateTokenAndGetUserId(token))));
    }

    public HouseResponse getHouse(Integer houseId, UUID token) {
        HouseEntity house = houseRepository.findById(houseId)
                .orElseThrow(() -> new MTrackerServiceException(MTrackerServiceError.NOT_FOUND));
        if (house.getOwnerId().equals(ssoClientService.validateTokenAndGetUserId(token))) {
            return mapper.mapHouseEntityToResponse(house);
        } else {
            throw new MTrackerServiceException(MTrackerServiceError.NOT_AUTHORIZED);
        }
    }

    public HouseResponse removeHouse(Integer houseId, UUID token) {
        HouseEntity house = houseRepository.findById(houseId)
                .orElseThrow(() -> new MTrackerServiceException(MTrackerServiceError.NOT_FOUND));
        if (house.getOwnerId().equals(ssoClientService.validateTokenAndGetUserId(token))) {
            houseRepository.delete(house);
            return mapper.mapHouseEntityToResponse(house);
        } else {
            throw new MTrackerServiceException(MTrackerServiceError.NOT_AUTHORIZED);
        }
    }

    public CarResponse addCar(CarPostRequest request, UUID token) {
        return mapper.mapCarEntityToResponse(
                carRepository.save(
                        new CarEntity(
                                request.getCarName(),
                                request.getDescription(),
                                ssoClientService.validateTokenAndGetUserId(token))));
    }

    public CarResponse getCar(Integer carId, UUID token) {
        CarEntity car = carRepository.findById(carId)
                .orElseThrow(() -> new MTrackerServiceException(MTrackerServiceError.NOT_FOUND));
        if (car.getOwnerId().equals(ssoClientService.validateTokenAndGetUserId(token))) {
            return mapper.mapCarEntityToResponse(car);
        } else {
            throw new MTrackerServiceException(MTrackerServiceError.NOT_AUTHORIZED);
        }
    }

    public CarResponse removeCar(Integer carId, UUID token) {
        CarEntity car = carRepository.findById(carId)
                .orElseThrow(() -> new MTrackerServiceException(MTrackerServiceError.NOT_FOUND));
        if (car.getOwnerId().equals(ssoClientService.validateTokenAndGetUserId(token))) {
            carRepository.delete(car);
            return mapper.mapCarEntityToResponse(car);
        } else {
            throw new MTrackerServiceException(MTrackerServiceError.NOT_AUTHORIZED);
        }
    }

    public MeterResponse addMeterToHouse(int houseId, MeterPostRequest request, UUID token) {
        Integer userId = ssoClientService.validateTokenAndGetUserId(token);
        HouseEntity house = houseRepository.findById(houseId).orElseThrow(() -> new MTrackerServiceException(MTrackerServiceError.NOT_FOUND));
        if (!house.getOwnerId().equals(userId)) {
            throw new MTrackerServiceException(MTrackerServiceError.NOT_AUTHORIZED);
        }
        MeterEntity meter = new MeterEntity(request.getMeterName(), request.getDescription(), request.getUnit());
        house.addMeter(meter);
        return mapper.mapMeterEntityToResponse(
                houseRepository.save(house).getMeters()
                        .stream().filter((meterEntity) -> meterEntity.getMeterName().equals(meter.getMeterName()))
                        .findFirst().orElseThrow(() -> new MTrackerServiceException(MTrackerServiceError.INTERNAL_ERROR)));
    }

    public MeterResponse addMeterToCar(int carId, MeterPostRequest request, UUID token) {
        Integer userId = ssoClientService.validateTokenAndGetUserId(token);
        CarEntity car = carRepository.findById(carId).orElseThrow(() -> new MTrackerServiceException(MTrackerServiceError.NOT_FOUND));
        if (!car.getOwnerId().equals(userId)) {
            throw new MTrackerServiceException(MTrackerServiceError.NOT_AUTHORIZED);
        }
        MeterEntity meter = new MeterEntity(request.getMeterName(), request.getDescription(), request.getUnit());
        car.addMeter(meter);
        carRepository.save(car);
        return mapper.mapMeterEntityToResponse(
                carRepository.save(car).getMeters()
                        .stream().filter((meterEntity) -> meterEntity.getMeterName().equals(meter.getMeterName()))
                        .findFirst().orElseThrow(() -> new MTrackerServiceException(MTrackerServiceError.INTERNAL_ERROR)));
    }

    public MeterResponse removeMeter(Integer meterId, UUID token) {
        MeterEntity meter = getMeterWithOwnerValidation(meterId, token);
        meterRepository.delete(meter);
        return mapper.mapMeterEntityToResponse(meter);
    }

    public MeterResponse getMeter(Integer meterId, UUID token) {
        return mapper.mapMeterEntityToResponse(getMeterWithOwnerValidation(meterId, token));
    }

    public MeasurementDto addMeasurementToMeter(Integer meterId, Double value, UUID token) {
        MeterEntity meter = getMeterWithOwnerValidation(meterId, token);
        MeasurementEntity measurement = new MeasurementEntity(value);
        meter.addMeasurement(measurement);
        meterRepository.save(meter);
        return mapper.mapMeasurementEntityToResponse(measurement);
    }

    public List<MeasurementDto> getMeasurementsForMeter(Integer meterId, UUID token) {
        MeterEntity meter = getMeterWithOwnerValidation(meterId, token);
        return meter.getMeasurements().stream().map(mapper::mapMeasurementEntityToResponse).collect(Collectors.toList());
    }

    private MeterEntity getMeterWithOwnerValidation(Integer meterId, UUID token) {
        Integer userId = ssoClientService.validateTokenAndGetUserId(token);

        MeterEntity meter = meterRepository.findById(meterId).orElseThrow(() -> new MTrackerServiceException(MTrackerServiceError.NOT_FOUND));

        if ((meter.getCar() != null && meter.getCar().getOwnerId().equals(userId)) ||
                (meter.getHouse() != null && meter.getHouse().getOwnerId().equals(userId))) {
            return meter;
        } else {
            throw new MTrackerServiceException(MTrackerServiceError.NOT_AUTHORIZED);
        }
    }


}
