package com.agh.riceitapi.dto;

import javax.validation.constraints.NotBlank;

public class FoodAddDTO extends FoodDTO{

    @NotBlank
    private long mealId;

    public long getMealId() {
        return mealId;
    }

    public void setMealId(long mealId) {
        this.mealId = mealId;
    }
}
