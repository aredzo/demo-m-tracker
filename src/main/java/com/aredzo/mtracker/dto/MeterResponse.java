package com.aredzo.mtracker.dto;

public class MeterResponse {

    private Integer meterId;

    private String meterName;

    private String description;

    private String unit;

    private Integer houseId;

    private Integer carId;

    public MeterResponse() {
    }

    public MeterResponse(Integer meterId, String meterName, String description, String unit, Integer houseId, Integer carId) {
        this.meterId = meterId;
        this.meterName = meterName;
        this.description = description;
        this.unit = unit;
        this.houseId = houseId;
        this.carId = carId;
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

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    @Override
    public String toString() {
        return "MeterResponse{" +
                "meterId=" + meterId +
                ", meterName='" + meterName + '\'' +
                ", description='" + description + '\'' +
                ", unit='" + unit + '\'' +
                ", houseId=" + houseId +
                ", carId=" + carId +
                '}';
    }
}
