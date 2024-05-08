package com.example.api.controller;

import com.example.api.payload.request.StudentDTORequest;
import com.example.api.payload.request.TeacherDTORequest;
import com.example.api.payload.response.TeacherResponse;
import com.example.api.service.StudentService;
import com.example.api.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping("/teachers")
    public Page<TeacherResponse> getAll(@RequestParam(defaultValue = "0") int page) {
        return teacherService.getAll(page);
    }

    @PostMapping("/teachers")
    public void createStudent(@RequestBody TeacherDTORequest request) {
        teacherService.createTeacher(request);
    }

}
