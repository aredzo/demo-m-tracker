package com.aredzo.mtracker.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NotNull
public class MeterPostRequest {

    @NotEmpty
    private String meterName;

    private String description;

    @NotEmpty
    private String unit;

    public MeterPostRequest() {
    }

    public MeterPostRequest(@NotEmpty String meterName, String description, @NotEmpty String unit) {
        this.meterName = meterName;
        this.description = description;
        this.unit = unit;
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

    @Override
    public String toString() {
        return "MeterPostRequest{" +
                "meterName='" + meterName + '\'' +
                ", description='" + description + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
