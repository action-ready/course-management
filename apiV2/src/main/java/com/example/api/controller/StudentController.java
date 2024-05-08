package com.example.api.controller;


import com.example.api.payload.request.StudentDTORequest;
import com.example.api.payload.response.ScheduleCheckStartDate;
import com.example.api.payload.response.StudentResponse;
import com.example.api.payload.response.StudentResponseDetails;
import com.example.api.repository.ScheduleRepository;
import com.example.api.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/students")
    public void createStudent(@RequestBody StudentDTORequest request) {
        studentService.createStudent(request);
    }

    @GetMapping("/students")
    public Page<StudentResponse> getAll(@RequestParam(defaultValue = "") String studentCode,
                                        @RequestParam(defaultValue = "0") int page) {
        return studentService.getAllStudent(studentCode, page);
    }

    @GetMapping("/course/{id}/students")
    public List<StudentResponse> getAllByCourseId(@PathVariable Long id) {
        return studentService.getAllStudentByCourseId(id);
    }

    @GetMapping("/students/{id}")
    public StudentResponseDetails getAll(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

}
