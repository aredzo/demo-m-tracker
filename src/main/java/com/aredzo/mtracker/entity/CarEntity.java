package com.aredzo.mtracker.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "car")
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer carId;

    @Column(nullable = false)
    private String carName;

    private String description;

    @Column(nullable = false)
    private Integer ownerId;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "car",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MeterEntity> meters = new ArrayList<>();

    public CarEntity() {
    }

    public CarEntity(String carName, String description, Integer ownerId) {
        this.carName = carName;
        this.description = description;
        this.ownerId = ownerId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public List<MeterEntity> getMeters() {
        return meters;
    }

    public void setMeters(List<MeterEntity> meters) {
        this.meters = meters;
    }

    public void addMeter(MeterEntity meter){
        this.meters.add(meter);
        meter.setCar(this);

    }

    public void removeMeter(MeterEntity meter){
        this.meters.remove(meter);
        meter.setCar(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarEntity carEntity = (CarEntity) o;
        return Objects.equals(carId, carEntity.carId) &&
                Objects.equals(carName, carEntity.carName) &&
                Objects.equals(description, carEntity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId, carName, description);
    }

    @Override
    public String toString() {
        return "CarEntity{" +
                "carId=" + carId +
                ", carName='" + carName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
