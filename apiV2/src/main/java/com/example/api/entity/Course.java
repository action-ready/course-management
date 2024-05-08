package com.example.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "Course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    private String name;
    private Date startDate;
    private Date endDate;
    private Integer price;
    private String imageUrl;
    private String description;
    private String language;
    private String studyTime;
    @OneToMany(mappedBy = "course")
    private Set<Semester> semesters;
}
