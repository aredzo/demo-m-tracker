package com.aredzo.mtracker.repository;

import com.aredzo.mtracker.entity.MeterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeterRepository extends JpaRepository<MeterEntity, Integer> {
}
