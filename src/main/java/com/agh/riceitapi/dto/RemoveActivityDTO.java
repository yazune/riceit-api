package com.agh.riceitapi.dto;

import javax.validation.constraints.NotBlank;

public class RemoveActivityDTO {

    @NotBlank
    private long activityId;

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activitId) {
        this.activityId = activitId;
    }
}
