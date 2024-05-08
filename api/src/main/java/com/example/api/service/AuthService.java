package com.example.api.service;

import com.example.api.payload.request.LoginRequest;
import com.example.api.payload.response.LoginResponse;
import com.example.api.payload.response.TokenResponse;

public interface AuthService {


    LoginResponse login(LoginRequest request);
    TokenResponse getNewToken(String  refreshToken);
}