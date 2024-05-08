package com.example.api.payload.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClazzDTORequest {

    @NotNull
    private Long teacherId;
    @NotNull
    private Long courseId;
    private String status;
    private String startDate;
    private String endDate;


}
