package com.agh.riceitapi.dto;

import javax.validation.constraints.NotBlank;

public class FoodDTO {

    @NotBlank
    private String name;

    @NotBlank
    private double kcal;

    @NotBlank
    private double carbohydrate;

    @NotBlank
    private double fat;

    @NotBlank
    private double protein;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getKcal() {
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }
}
