package com.example.api.payload.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private Long id;
    private String username;
    private Long teacherId;
    private Long studentId;
    private String role;
    private String token;
    private String refreshToken;
}
