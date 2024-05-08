package com.example.api.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public interface ScheduleCheckStartDate {
    @JsonProperty("startDate")
    Date getStartDate();

    @JsonProperty("scheduleId")
    Long getScheduleId();

}
