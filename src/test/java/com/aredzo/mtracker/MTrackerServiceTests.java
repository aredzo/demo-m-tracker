package com.aredzo.mtracker;


import com.aredzo.mtracker.dto.CarPostRequest;
import com.aredzo.mtracker.dto.CarResponse;
import com.aredzo.mtracker.dto.HousePostRequest;
import com.aredzo.mtracker.dto.HouseResponse;
import com.aredzo.mtracker.dto.MeasurementDto;
import com.aredzo.mtracker.dto.MeterPostRequest;
import com.aredzo.mtracker.dto.MeterResponse;
import com.aredzo.mtracker.entity.CarEntity;
import com.aredzo.mtracker.entity.HouseEntity;
import com.aredzo.mtracker.entity.MeasurementEntity;
import com.aredzo.mtracker.entity.MeterEntity;
import com.aredzo.mtracker.repository.CarRepository;
import com.aredzo.mtracker.repository.HouseRepository;
import com.aredzo.mtracker.repository.MeasurementRepository;
import com.aredzo.mtracker.repository.MeterRepository;
import com.aredzo.mtracker.service.MTrackerService;
import com.aredzo.mtracker.service.SsoClientService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
@RunWith(SpringRunner.class)
public class MTrackerServiceTests {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private MeterRepository meterRepository;

    @Autowired
    private MeasurementRepository measurementRepository;

    @MockBean
    private SsoClientService ssoClientService;

    private MTrackerService mTrackerService;

    @Before
    public void setUp() {
        measurementRepository.deleteAll();
        meterRepository.deleteAll();
        carRepository.deleteAll();
        houseRepository.deleteAll();

        when(ssoClientService.validateTokenAndGetUserId(any())).thenReturn(1);
        mTrackerService = new MTrackerService(carRepository,
                houseRepository,
                measurementRepository,
                meterRepository,
                ssoClientService);


    }

    @After
    public void tearDown() {
        measurementRepository.deleteAll();
        meterRepository.deleteAll();
        carRepository.deleteAll();
        houseRepository.deleteAll();
    }

    @Test
    public void addHouseNominalTest() {

        HouseResponse response = mTrackerService.addHouse(new HousePostRequest("house-1", null), UUID.randomUUID());

        List<HouseEntity> allHouses = houseRepository.findAll();
        assertThat(allHouses).hasSize(1);
        assertThat(allHouses.get(0).getOwnerId()).isEqualTo(1);
        assertThat(allHouses.get(0).getHouseName()).isEqualTo("house-1");
        assertThat(allHouses.get(0).getDescription()).isNull();
        assertThat(allHouses.get(0).getMeters()).isEmpty();

        assertThat(response.getHouseId()).isEqualTo(allHouses.get(0).getHouseId());
        assertThat(response.getOwnerId()).isEqualTo(1);
        assertThat(response.getHouseName()).isEqualTo("house-1");
        assertThat(response.getDescription()).isNull();
        assertThat(response.getMeterIds()).isEmpty();
    }

    @Test
    public void addCarNominalTest() {

        CarResponse response = mTrackerService.addCar(new CarPostRequest("car-1", null), UUID.randomUUID());

        List<CarEntity> allCars = carRepository.findAll();
        assertThat(allCars).hasSize(1);
        assertThat(allCars.get(0).getOwnerId()).isEqualTo(1);
        assertThat(allCars.get(0).getCarName()).isEqualTo("car-1");
        assertThat(allCars.get(0).getDescription()).isNull();
        assertThat(allCars.get(0).getMeters()).isEmpty();

        assertThat(response.getCarId()).isEqualTo(allCars.get(0).getCarId());
        assertThat(response.getOwnerId()).isEqualTo(1);
        assertThat(response.getCarName()).isEqualTo("car-1");
        assertThat(response.getDescription()).isNull();
        assertThat(response.getMeterIds()).isEmpty();
    }

    @Test
    public void addMeterToHouseNominalTest() {
        HouseEntity house = houseRepository.save(new HouseEntity("house-1", null, 1));

        MeterResponse response = mTrackerService.addMeterToHouse(house.getHouseId(),
                new MeterPostRequest("meter-1", null, "m3"),
                UUID.randomUUID());

        List<MeterEntity> allMeters = meterRepository.findAll();
        assertThat(allMeters).hasSize(1);
        assertThat(allMeters.get(0).getHouse()).isEqualTo(house);
        assertThat(allMeters.get(0).getCar()).isNull();
        assertThat(allMeters.get(0).getMeterName()).isEqualTo("meter-1");
        assertThat(allMeters.get(0).getDescription()).isNull();
        assertThat(allMeters.get(0).getUnit()).isEqualTo("m3");

        MeterEntity meter = allMeters.get(0);

        assertThat(houseRepository.findById(house.getHouseId()).orElseThrow(RuntimeException::new).getMeters())
                .contains(meter);
        assertThat(houseRepository.findById(house.getHouseId()).orElseThrow(RuntimeException::new).getMeters())
                .hasSize(1);


        assertThat(response.getMeterId()).isEqualTo(meter.getMeterId());
        assertThat(response.getHouseId()).isEqualTo(house.getHouseId());
        assertThat(response.getCarId()).isNull();
        assertThat(response.getMeterName()).isEqualTo("meter-1");
        assertThat(response.getUnit()).isEqualTo("m3");
        assertThat(response.getDescription()).isNull();
    }


    @Test
    public void addMeterToCarNominalTest() {
        CarEntity car = carRepository.save(new CarEntity("car-1", null, 1));

        MeterResponse response = mTrackerService.addMeterToCar(car.getCarId(),
                new MeterPostRequest("meter-1", null, "m3"),
                UUID.randomUUID());

        List<MeterEntity> allMeters = meterRepository.findAll();
        assertThat(allMeters).hasSize(1);
        assertThat(allMeters.get(0).getCar()).isEqualTo(car);
        assertThat(allMeters.get(0).getHouse()).isNull();
        assertThat(allMeters.get(0).getMeterName()).isEqualTo("meter-1");
        assertThat(allMeters.get(0).getDescription()).isNull();
        assertThat(allMeters.get(0).getUnit()).isEqualTo("m3");

        MeterEntity meter = allMeters.get(0);

        assertThat(carRepository.findById(car.getCarId()).orElseThrow(RuntimeException::new).getMeters())
                .contains(meter);
        assertThat(carRepository.findById(car.getCarId()).orElseThrow(RuntimeException::new).getMeters())
                .hasSize(1);


        assertThat(response.getMeterId()).isEqualTo(meter.getMeterId());
        assertThat(response.getCarId()).isEqualTo(car.getCarId());
        assertThat(response.getHouseId()).isNull();
        assertThat(response.getMeterName()).isEqualTo("meter-1");
        assertThat(response.getUnit()).isEqualTo("m3");
        assertThat(response.getDescription()).isNull();
    }

    @Test
    public void addMeasurementToMeterNominalTest() {
        Instant start = Instant.now();
        MeterEntity meter = new MeterEntity("meter-1", null, "m3");
        CarEntity car = new CarEntity("car-1", null, 1);
        car.addMeter(meter);
        carRepository.save(car);
        int meterId = car.getMeters().get(0).getMeterId();

        MeasurementDto response = mTrackerService.addMeasurementToMeter(
                meterId,
                0.01,
                UUID.randomUUID());

        List<MeasurementEntity> allMeasurements = measurementRepository.findAll();
        assertThat(allMeasurements).hasSize(1);
        assertThat(allMeasurements.get(0).getMeter()).isEqualTo(meter);
        assertThat(allMeasurements.get(0).getValue()).isEqualTo(0.01);
        assertThat(allMeasurements.get(0).getTimestamp()).isBetween(start, Instant.now());

        MeterEntity meter1 = meterRepository.findById(meterId).orElseThrow(RuntimeException::new);

        assertThat(meter1.getMeasurements()).contains(allMeasurements.get(0));
        assertThat(meter1.getMeasurements()).hasSize(1);


        assertThat(response.getValue()).isEqualTo(0.01);
    }
}
