package com.example.api.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDTORequest {

    @NotBlank
    private String name;
    private String startDate;
    private String endDate;
    @NotNull
    private Double price;
    @NotBlank
    private String description;
    @NotBlank
    private String language;
    @NotBlank
    private String studyTime;

    @NotBlank
    private String imageUrl;
}
