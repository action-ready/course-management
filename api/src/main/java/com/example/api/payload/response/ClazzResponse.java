package com.example.api.payload.response;


import com.example.api.entity.Clazz;
import com.example.api.entity.Course;
import com.example.api.entity.Teacher;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ClazzResponse {

    private Long id;
    private String clazzCode;
    private String teacherFullName;
    private String courseName;
    private String courseImageUrl;
    private Long courseId;


}
