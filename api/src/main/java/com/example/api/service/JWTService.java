package com.example.api.service;

import com.example.api.entity.Account;
import com.example.api.entity.Token;
import com.example.api.payload.response.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface JWTService {

    String generateJWTToken(String username);

    Authentication parseTokenToUserInformation(HttpServletRequest request);
    Token generateRefreshToken(Account account);

    TokenResponse getNewToken(String refreshToken);
}
