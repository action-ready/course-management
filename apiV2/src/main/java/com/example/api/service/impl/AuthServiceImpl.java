package com.example.api.service.impl;

import com.example.api.entity.Account;
import com.example.api.entity.Student;
import com.example.api.entity.Teacher;
import com.example.api.entity.Token;
import com.example.api.payload.request.LoginRequest;
import com.example.api.payload.response.LoginResponse;
import com.example.api.payload.response.TokenResponse;
import com.example.api.repository.AccountRepository;
import com.example.api.repository.StudentRepository;
import com.example.api.repository.TeacherRepository;
import com.example.api.repository.TokenRepository;
import com.example.api.service.AuthService;
import com.example.api.service.JWTService;
import com.example.api.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final ValidationService validationService;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final TokenRepository tokenRepository;
    private final JWTService jwtService;

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        validationService.validate(request);
        Account account = accountRepository.findAccountByUsername(request.getUsername());
        tokenRepository.deleteByTypeAndAccount(Token.Type.REFRESH_TOKEN, account);
        String token = jwtService.generateJWTToken(account.getUsername());
        Token refreshToken = jwtService.generateRefreshToken(account);

        Optional<Teacher> teacher = teacherRepository.findByAccount(account);
        Optional<Student> student = studentRepository.findByAccount(account);


        Long teacherId = null;
        if(teacher.isPresent()) {
            teacherId = teacher.get().getId();
        }
        Long studentId = null;
        if(student.isPresent()) {
            studentId = student.get().getId();
        }
        return LoginResponse.builder()
                .token(token)
                .role(account.getRole().toString())
                .id(account.getId())
                .studentId(studentId)
                .teacherId(teacherId)
                .username(account.getUsername())
                .refreshToken(refreshToken.getKey().toString())
                .build();
    }



    @Override
    public TokenResponse getNewToken(String refreshToken) {
        return jwtService.getNewToken(refreshToken);
    }
}
