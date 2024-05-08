package com.example.api.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private String key;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredDate;


    public enum Type {
        REFRESH_TOKEN, REGISTER, FORGOT_PASSWORD
    }
}

