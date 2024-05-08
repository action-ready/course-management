package com.example.api.service;

import com.example.api.payload.request.StudentDTORequest;
import com.example.api.payload.response.StudentResponse;
import com.example.api.payload.response.StudentResponseDetails;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {


    void createStudent(StudentDTORequest request);

    Page<StudentResponse> getAllStudent(String studentCode, int page );

    List<StudentResponse> getAllStudentByCourseId(Long courseId);

    StudentResponseDetails getStudentById(Long studentId);

}
