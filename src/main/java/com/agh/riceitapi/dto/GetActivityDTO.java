package com.agh.riceitapi.dto;

import javax.validation.constraints.NotBlank;

public class GetActivityDTO {

    @NotBlank
    private long activityId;

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }
}
