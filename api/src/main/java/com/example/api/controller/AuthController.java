package com.example.api.controller;


import com.example.api.payload.request.LoginRequest;
import com.example.api.payload.response.TokenResponse;
import com.example.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/refreshToken")
    public TokenResponse refreshToken(@RequestParam(name = "refreshToken") String refreshToken) {
        return authService.getNewToken(refreshToken);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {

            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return ResponseEntity.ok(authService.login(request));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }
    }
}