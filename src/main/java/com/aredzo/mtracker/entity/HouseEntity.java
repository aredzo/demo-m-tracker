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
@Table(name = "house")
public class HouseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer houseId;

    @Column(nullable = false)
    private String houseName;

    private String description;

    @Column(nullable = false)
    private Integer ownerId;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "house",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MeterEntity> meters = new ArrayList<>();


    public HouseEntity() {
    }

    public HouseEntity(String houseName, String description, Integer ownerId) {
        this.houseName = houseName;
        this.description = description;
        this.ownerId = ownerId;
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

    public List<MeterEntity> getMeters() {
        return meters;
    }

    public void addMeter(MeterEntity meter) {
        this.meters.add(meter);
        meter.setHouse(this);
    }

    public void removeMeter(MeterEntity meter) {
        this.meters.remove(meter);
        meter.setHouse(null);
    }

    public void setOwners(Integer owners) {
        this.ownerId = owners;
    }

    public void setMeters(List<MeterEntity> meters) {
        this.meters = meters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseEntity that = (HouseEntity) o;
        return Objects.equals(houseId, that.houseId) &&
                Objects.equals(houseName, that.houseName) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseId, houseName, description);
    }

    @Override
    public String toString() {
        return "HouseEntity{" +
                "houseId=" + houseId +
                ", houseName='" + houseName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
