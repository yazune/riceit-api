package com.agh.riceitapi.model;

import com.agh.riceitapi.dto.FoodDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "foods")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    private double kcal;

    @NotNull
    private double protein;

    @NotNull
    private double carbohydrate;

    @NotNull
    private double fat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Meal meal;

    public Food() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
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

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public void fillWithDataFrom(FoodDTO foodDTO){
        this.name = foodDTO.getName();
        this.kcal = foodDTO.getKcal();
        this.protein = foodDTO.getProtein();
        this.fat = foodDTO.getFat();
        this.carbohydrate = foodDTO.getCarbohydrate();
    }

}
