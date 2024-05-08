package com.example.api.service;

import com.example.api.entity.Account;
import com.example.api.payload.response.StudentResponse;
import com.example.api.payload.response.TeacherDTOResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface AccountService {

    List<TeacherDTOResponse>getAllTeacher();

    List<StudentResponse> getAllStudent();

    Account getAccountByUsername(String username);

    UserDetails loadByUsername(String username);
}
