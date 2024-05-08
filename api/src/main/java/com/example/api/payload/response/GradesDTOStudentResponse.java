package com.example.api.payload.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GradesDTOStudentResponse {

    private Long studentId;
    private Long studentCode;
    private String fullName;
    private Double grade;
}
