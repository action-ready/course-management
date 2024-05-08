package com.example.api.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Account")
public class Account{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;
    @Column(unique = true)
    private String username;
    @Column(length = 800)
    private String password;
    private Status status;
    private Role role;

    public enum Status {
        ACTIVE,
        BLOCK
    }

    public enum Role {
        ADMIN,
        STUDENT,
        TEACHER
    }
}