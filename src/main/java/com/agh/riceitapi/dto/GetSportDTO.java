package com.agh.riceitapi.dto;

import javax.validation.constraints.NotBlank;

public class GetSportDTO {

    @NotBlank
    private long sportId;

    public long getSportId() {
        return sportId;
    }

    public void setSportId(long sportId) {
        this.sportId = sportId;
    }
}
