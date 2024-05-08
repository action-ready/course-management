package com.example.api.payload.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleDTORequest {


    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String detailsSemester;
    private String startDate;
    private String endDate;
    private Integer numberOfSessions;


}
