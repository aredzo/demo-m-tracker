package com.aredzo.mtracker.repository;

import com.aredzo.mtracker.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<CarEntity, Integer> {

    Optional<CarEntity> findById(Integer carId);
}
