package com.example.api.payload.response;


import com.example.api.entity.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class TeacherResponseDetails {

    private Long id;
    private String fullName;


    private List<Clazz> clazzes;

    @Data
    static class Clazz {
        private Long id;
        private String clazzCode;
        private Date startDate;
        private Date endDate;
        private Course courseName;

        @ManyToMany
        @JoinTable(
                name = "clazz_student",
                joinColumns = @JoinColumn(name = "clazz_id"),
                inverseJoinColumns = @JoinColumn(name = "student_id")
        )
        private Set<Student> students;


        @Data
        static class Student {
            private Long id;

            private String studentCode;

            private String fullName;
        }

    }
}
