package com.agh.riceitapi.dto;

import javax.validation.constraints.NotBlank;

public class AddSportDTO {

    @NotBlank
    private String date;

    @NotBlank
    private String name;

    @NotBlank
    private double kcalBurnt;

    @NotBlank
    private int duration;

    @NotBlank
    private String sportType;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public AddSportDTO() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
