package com.aredzo.mtracker.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NotNull
public class CarPostRequest {

    @NotEmpty
    private String carName;

    private String description;

    public CarPostRequest() {
    }

    public CarPostRequest(@NotEmpty String carName, String description) {
        this.carName = carName;
        this.description = description;
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

    @Override
    public String toString() {
        return "CarPostRequest{" +
                "carName='" + carName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
