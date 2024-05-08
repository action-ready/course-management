package com.example.api.service.impl;


import com.example.api.entity.Account;
import com.example.api.entity.Student;
import com.example.api.entity.Teacher;
import com.example.api.payload.request.TeacherDTORequest;
import com.example.api.payload.response.TeacherResponse;
import com.example.api.repository.AccountRepository;
import com.example.api.repository.TeacherRepository;
import com.example.api.service.TeacherService;
import com.example.api.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final ModelMapper modelMapper;
    private final TeacherRepository teacherRepository;
    private final AccountRepository accountRepository;
    private final ValidationService validationService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createTeacher(TeacherDTORequest request) {
        validationService.validate(request);

        Teacher teacher = modelMapper.map(request, Teacher.class);
        teacher.setId(null);
        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setStatus(Account.Status.ACTIVE);
        account.setPassword(passwordEncoder.encode("123456"));
        account.setRole(Account.Role.TEACHER);

        Account accountSave = accountRepository.save(account);
        teacher.setAccount(accountSave);
        teacherRepository.save(teacher);
    }

    @Override
    public Page<TeacherResponse> getAll(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());

        Page<Teacher> teachers = teacherRepository.findAll(pageable);

        Page<TeacherResponse> responses = teachers.map(teacher -> {
            TeacherResponse response = modelMapper.map(teacher, TeacherResponse.class);
            response.setNumberOfClazzes();
            return response;
        });
        return responses;
    }
}
