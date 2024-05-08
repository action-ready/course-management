package com.example.api.payload.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradesDTORequest {


    private Long id;
    private Double grade;
    private Long clazzId;
    private Long studentId;
    private Long scheduleId;
}
