package com.example.api.service.impl;


import com.example.api.entity.Account;
import com.example.api.entity.Student;
import com.example.api.entity.Teacher;
import com.example.api.payload.response.StudentResponse;
import com.example.api.payload.response.TeacherDTOResponse;
import com.example.api.repository.AccountRepository;
import com.example.api.repository.StudentRepository;
import com.example.api.repository.TeacherRepository;
import com.example.api.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;

    @Override
    public List<TeacherDTOResponse> getAllTeacher() {

        List<Teacher> teachers = teacherRepository.findAll();

        List<TeacherDTOResponse> responses = teachers.stream()
                .map(teacher -> modelMapper.map(teacher, TeacherDTOResponse.class))
                .collect(Collectors.toList());
        return responses;
    }

    @Override
    public List<StudentResponse> getAllStudent() {
        List<Student> students = studentRepository.findAll();
        List<StudentResponse> responses = students.stream()
                .map(student -> modelMapper.map(student, StudentResponse.class))
                .collect(Collectors.toList());

        return responses;
    }

    @Override
    public Account getAccountByUsername(String username) {
        return accountRepository.findAccountByUsername(username);

    }

    @Override
    public UserDetails loadByUsername(String username) {
        Account account = accountRepository.findAccountByUsername(username);

        if (account == null) {
            throw new UsernameNotFoundException(username);
        }

        return new User(
                account.getUsername(),
                account.getPassword(),
                AuthorityUtils.createAuthorityList(account.getRole().toString())
        );
    }
}
