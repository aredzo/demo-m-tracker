package com.aredzo.mtracker.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NotNull
public class HousePostRequest {

    @NotEmpty
    private String houseName;

    private String description;

    public HousePostRequest() {
    }

    public HousePostRequest(@NotEmpty String houseName, String description) {
        this.houseName = houseName;
        this.description = description;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "HousePostRequest{" +
                "houseName='" + houseName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
