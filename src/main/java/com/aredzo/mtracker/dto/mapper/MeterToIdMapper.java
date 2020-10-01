package com.aredzo.mtracker.dto.mapper;

import com.aredzo.mtracker.entity.MeterEntity;

public class MeterToIdMapper {

    public Integer map(MeterEntity meterEntity){
        return meterEntity.getMeterId();
    }
}
