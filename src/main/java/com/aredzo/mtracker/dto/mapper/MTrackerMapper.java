package com.aredzo.mtracker.dto.mapper;


import com.aredzo.mtracker.dto.CarResponse;
import com.aredzo.mtracker.dto.HouseResponse;
import com.aredzo.mtracker.dto.MeasurementDto;
import com.aredzo.mtracker.dto.MeterResponse;
import com.aredzo.mtracker.entity.CarEntity;
import com.aredzo.mtracker.entity.HouseEntity;
import com.aredzo.mtracker.entity.MeasurementEntity;
import com.aredzo.mtracker.entity.MeterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = MeterToIdMapper.class)
public interface MTrackerMapper {

    @Mapping(source = "meters", target = "meterIds")
    HouseResponse mapHouseEntityToResponse(HouseEntity houseEntity);

    @Mapping(source = "meters", target = "meterIds")
    CarResponse mapCarEntityToResponse(CarEntity carEntity);

    @Mapping(source = "house.houseId", target = "houseId")
    @Mapping(source = "car.carId", target = "carId")
    MeterResponse mapMeterEntityToResponse(MeterEntity meterEntity);

    MeasurementDto mapMeasurementEntityToResponse(MeasurementEntity measurementEntity);
}
