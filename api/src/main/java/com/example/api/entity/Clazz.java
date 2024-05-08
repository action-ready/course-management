package com.example.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "Clazz")
public class Clazz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clazz_id")
    private Long id;

    private String clazzCode;

    private Date startDate;
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private Status status;


    @ManyToMany
    @JoinTable(
            name = "clazz_student",
            joinColumns = @JoinColumn(name = "clazz_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students;


    public enum Status {
        ACTIVE, BLOCK
    }

}
