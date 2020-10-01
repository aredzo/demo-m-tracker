package com.aredzo.mtracker.dto;

import java.time.Instant;

public class MeasurementDto {

    private Double value;

    private Instant timestamp;

    public MeasurementDto() {
    }

    public MeasurementDto(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "MeasurementDto{" +
                "value=" + value +
                ", timestamp=" + timestamp +
                '}';
    }
}
