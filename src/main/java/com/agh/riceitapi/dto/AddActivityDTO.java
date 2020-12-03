package com.agh.riceitapi.dto;

import javax.validation.constraints.NotBlank;

public class AddActivityDTO {

    @NotBlank
    private String date;

    @NotBlank
    private String name;

    @NotBlank
    private double kcalBurnt;

    public AddActivityDTO() {
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
