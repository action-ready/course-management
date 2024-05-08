package com.example.api.payload.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherDTORequest {

    @NotBlank
    private String fullName;

    @NotBlank
    private String username;


}
