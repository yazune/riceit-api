package com.agh.riceitapi.dto;

import com.agh.riceitapi.model.Meal;
import com.agh.riceitapi.model.Sport;

import java.util.List;

public class AllSportsDTO {

    private List<Sport> sports;

    public AllSportsDTO(List<Sport> sports) {
        this.sports = sports;
    }

    public List<Sport> getSports() {
        return sports;
    }

    public void setSports(List<Sport> sports) {
        this.sports = sports;
    }
}
