package com.example.api.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface StudentWithGradeDTO {
    @JsonProperty("studentId")
    Long getStudentId();
    @JsonProperty("gradesId")
    Long getGradesId();

    @JsonProperty("studentCode")
    String getStudentCode();

    @JsonProperty("fullName")
    String getFullName();

    @JsonProperty("grade")
    Double getGrade();
}

