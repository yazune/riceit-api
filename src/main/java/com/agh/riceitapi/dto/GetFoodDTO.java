package com.agh.riceitapi.dto;

import javax.validation.constraints.NotBlank;

public class GetFoodDTO {

    @NotBlank
    private long foodId;

    public long getFoodId() {
        return foodId;
    }

    public void setFoodId(long foodId) {
        this.foodId = foodId;
    }
}
