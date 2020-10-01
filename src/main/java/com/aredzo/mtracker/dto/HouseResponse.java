package com.aredzo.mtracker.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NotNull
public class HouseResponse {

    @NotNull
    private Integer houseId;

    @NotEmpty
    private String houseName;

    private String description;

    private Integer ownerId;

    private List<Integer> meterIds = new ArrayList<>();

    public HouseResponse() {
    }

    public HouseResponse(@NotNull Integer houseId, @NotEmpty String houseName, String description, Integer ownerId, List<Integer> meterIds) {
        this.houseId = houseId;
        this.houseName = houseName;
        this.description = description;
        this.ownerId = ownerId;
        this.meterIds = meterIds;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
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
        return "HouseResponse{" +
                "houseId=" + houseId +
                ", houseName='" + houseName + '\'' +
                ", description='" + description + '\'' +
                ", ownerId=" + ownerId +
                ", meterIds=" + meterIds +
                '}';
    }
}
