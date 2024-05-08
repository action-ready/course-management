package com.example.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "Student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    private String studentCode;

    private String fullName;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "student")
    private List<Grades> grades;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
