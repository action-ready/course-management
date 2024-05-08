package com.example.api.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface ScheduleWithClazzResponse {

    @JsonProperty("scheduleId")
    Long getScheduleId();

    @JsonProperty("name")
    String getName();

    @JsonProperty("semesterName")
    String getSemesterName();

    @JsonProperty("status")
    String getStatus();

    String getStartDate();

    String getEndDate();

    Integer getNumberOfSessions();
}
