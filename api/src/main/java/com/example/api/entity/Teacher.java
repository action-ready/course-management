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
@Table(name = "Teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long id;

    private String fullName;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "teacher")
    private List<Clazz> clazzes;
}
