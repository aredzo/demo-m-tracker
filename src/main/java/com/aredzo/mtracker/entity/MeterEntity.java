package com.aredzo.mtracker.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "meter")
public class MeterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer meterId;

    @Column(nullable = false)
    private String meterName;

    private String description;

    @Column(nullable = false)
    private String unit;

    @ManyToOne(fetch = FetchType.LAZY)
    private HouseEntity house;

    @ManyToOne(fetch = FetchType.LAZY)
    private CarEntity car;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "meter",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MeasurementEntity> measurements = new ArrayList<>();

    public MeterEntity() {
    }

    public MeterEntity(String meterName, String description, String unit) {
        this.meterName = meterName;
        this.description = description;
        this.unit = unit;
    }

    public Integer getMeterId() {
        return meterId;
    }

    public void setMeterId(Integer meterId) {
        this.meterId = meterId;
    }

    public String getMeterName() {
        return meterName;
    }

    public void setMeterName(String meterName) {
        this.meterName = meterName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public HouseEntity getHouse() {
        return house;
    }

    public void setHouse(HouseEntity house) {
        this.house = house;
    }

    public CarEntity getCar() {
        return car;
    }

    public void setCar(CarEntity car) {
        this.car = car;
    }

    public List<MeasurementEntity> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<MeasurementEntity> measurements) {
        this.measurements = measurements;
    }

    public void addMeasurement(MeasurementEntity measurement) {
        this.measurements.add(measurement);
        measurement.setMeter(this);
    }

    public void removeMeasurement(MeasurementEntity measurement) {
        this.measurements.remove(measurement);
        measurement.setMeter(null);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeterEntity that = (MeterEntity) o;
        return Objects.equals(meterId, that.meterId) &&
                Objects.equals(meterName, that.meterName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meterId, meterName, description, unit);
    }

    @Override
    public String toString() {
        return "MeterEntity{" +
                "meterId=" + meterId +
                ", meterName='" + meterName + '\'' +
                ", description='" + description + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
