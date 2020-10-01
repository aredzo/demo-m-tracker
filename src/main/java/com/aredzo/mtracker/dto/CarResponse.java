package com.aredzo.mtracker.dto;

import java.util.ArrayList;
import java.util.List;

public class CarResponse {

    private Integer carId;

    private String carName;

    private String description;

    private Integer ownerId;

    private List<Integer> meterIds = new ArrayList<>();

    public CarResponse() {
    }

    public CarResponse(Integer carId, String carName, String description, Integer ownerId, List<Integer> meterIds) {
        this.carId = carId;
        this.carName = carName;
        this.description = description;
        this.ownerId = ownerId;
        this.meterIds = meterIds;
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

    public List<Integer> getMeterIds() {
        return meterIds;
    }

    public void setMeterIds(List<Integer> meterIds) {
        this.meterIds = meterIds;
    }

    @Override
    public String toString() {
        return "CarResponse{" +
                "carId=" + carId +
                ", carName='" + carName + '\'' +
                ", description='" + description + '\'' +
                ", ownerId=" + ownerId +
                ", meterIds=" + meterIds +
                '}';
    }
}
