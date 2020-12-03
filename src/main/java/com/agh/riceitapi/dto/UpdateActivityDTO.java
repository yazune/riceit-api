package com.agh.riceitapi.dto;

import javax.validation.constraints.NotBlank;

public class UpdateActivityDTO {

    @NotBlank
    private long activityId;

    @NotBlank
    private String name;

    @NotBlank
    private double kcalBurnt;

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getKcalBurnt() {
        return kcalBurnt;
    }

    public void setKcalBurnt(double kcalBurnt) {
        this.kcalBurnt = kcalBurnt;
    }
}
