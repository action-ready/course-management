package com.example.api.service.impl;


import com.example.api.entity.Account;
import com.example.api.entity.Student;
import com.example.api.exception.GlobalException;
import com.example.api.payload.request.StudentDTORequest;
import com.example.api.payload.response.StudentResponse;
import com.example.api.payload.response.StudentResponseDetails;
import com.example.api.repository.AccountRepository;
import com.example.api.repository.SemesterRepository;
import com.example.api.repository.StudentRepository;
import com.example.api.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final SemesterRepository semesterRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createStudent(StudentDTORequest request) {
        Student student = modelMapper.map(request, Student.class);
        student.setId(null);
        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setStatus(Account.Status.ACTIVE);
        account.setPassword(passwordEncoder.encode("123456"));
        account.setRole(Account.Role.STUDENT);

        String studentCode = generateStudentCode();
        student.setStudentCode(studentCode);

        Account accountSave = accountRepository.save(account);
        student.setAccount(accountSave);
        studentRepository.save(student);

    }

    @Override
    public Page<StudentResponse> getAllStudent(String studentCode, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());

        Page<Student> students = studentRepository.findAll(pageable);

        Stream<Student> studentStream = students.stream();

        if (studentCode != null && !studentCode.isEmpty()) {
            studentStream = studentStream.filter(student -> student.getStudentCode().equalsIgnoreCase(studentCode));
        }

        List<StudentResponse> filteredStudents = studentStream
                .map(student -> modelMapper.map(student, StudentResponse.class))
                .collect(Collectors.toList());

        return new PageImpl<>(filteredStudents, pageable, students.getTotalElements());
    }

    @Override
    public List<StudentResponse> getAllStudentByCourseId(Long courseId) {
        List<Student> students = studentRepository.findAllByCourseId(courseId);

        List<StudentResponse> responses = students.stream()
                .map(student -> modelMapper.map(student, StudentResponse.class))
                .collect(Collectors.toList());
        return responses;
    }

    @Override
    public StudentResponseDetails getStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new GlobalException("Student not found"));

        StudentResponseDetails responseDetails = modelMapper.map(student, StudentResponseDetails.class);
        return responseDetails;
    }


    private String generateStudentCode() {
        String prefix = "SV";
        int numberLength = 5;

        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(prefix);

        for (int i = 0; i < numberLength; i++) {
            int randomNumber = random.nextInt(10);
            stringBuilder.append(randomNumber);
        }

        return stringBuilder.toString();
    }
}
