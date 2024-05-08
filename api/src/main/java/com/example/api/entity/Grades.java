package com.example.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "Grades")
public class Grades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grades_id")
    private Long id;

    private Double grade;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "clazz_id")
    private Clazz clazz;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

}
