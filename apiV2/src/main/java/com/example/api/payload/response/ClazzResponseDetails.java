package com.example.api.payload.response;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class ClazzResponseDetails {
    private Long id;
    private String clazzCode;
    private String teacherFullName;
    private String courseName;
    private Long courseId;
    private Date startDate;
    private Date endDate;
    private Set<Student> students;

    @Data
    static class Student {
        private Long id;
        private String fullName;
        private String studentCode;
    }
}
