package com.example.api.payload.response;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentResponseDetails {

    private Long id;
    private String studentCode;
    private String fullName;
    private List<Grades> grades;
    private String courseName;

    @Data
    static class Grades {
        private Long id;
        private Double grade;
        private String clazzClazzCode;
        private Long clazzId;
        private String scheduleName;
        private String scheduleSemesterName;

    }
}
