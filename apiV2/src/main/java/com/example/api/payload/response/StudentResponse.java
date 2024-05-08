package com.example.api.payload.response;


import com.example.api.entity.Course;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentResponse {

    private Long id;
    private String studentCode;
    private String fullName;
    private String courseName;
}
