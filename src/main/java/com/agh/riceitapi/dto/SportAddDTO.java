package com.agh.riceitapi.dto;

import javax.validation.constraints.NotBlank;

public class SportAddDTO extends SportDTO{

    @NotBlank
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
