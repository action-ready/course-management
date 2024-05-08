package com.example.api.payload.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDTORequest {

    @NotBlank
    private String fullName;

    @NotBlank
    private String username;

    @NotBlank
    private Long courseId;


}
