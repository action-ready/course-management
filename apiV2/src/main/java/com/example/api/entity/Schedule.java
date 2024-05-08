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
@Table(name = "Schedule")
public class Schedule {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @Column(unique = true)
    private String name;
    private String description;
    private Status status;
    private Date startDate;
    private Date endDate;
    private Integer numberOfSessions;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;


    public enum  Status {
        COMING_SOON,
        ACTIVE,
        BLOCK,
        UNFINISHED,
    }

}
