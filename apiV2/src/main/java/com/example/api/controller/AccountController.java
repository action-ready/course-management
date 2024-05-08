package com.example.api.controller;


import com.example.api.payload.response.StudentResponse;
import com.example.api.payload.response.TeacherDTOResponse;
import com.example.api.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;


    @GetMapping("/teachers")
    public List<TeacherDTOResponse> getAllTeachers() {
        return accountService.getAllTeacher();
    }

    @GetMapping("/students")
    public List<StudentResponse> getAllStudents() {
        return accountService.getAllStudent();
    }
}
