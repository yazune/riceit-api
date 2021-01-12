package com.agh.riceitapi.dto;

import com.agh.riceitapi.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class AllMealsDTO {

    private List<Meal> meals;

    public AllMealsDTO(List<Meal> meals) {
        this.meals = meals;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
