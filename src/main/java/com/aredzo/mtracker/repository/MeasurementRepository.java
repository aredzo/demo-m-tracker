package com.aredzo.mtracker.repository;

import com.aredzo.mtracker.entity.MeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<MeasurementEntity, Integer> {

    List<MeasurementEntity> findAllByMeterMeterId(Integer meterId);
}
