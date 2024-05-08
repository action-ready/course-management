package com.example.api.controller;


import com.example.api.payload.request.CourseDTORequest;
import com.example.api.payload.response.CourseDTOResponseDetails;
import com.example.api.payload.response.CourseDTOResponseSelect;
import com.example.api.payload.response.CourseResponse;
import com.example.api.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public Page<CourseResponse> getAll(Pageable pageable) {
        return courseService.getAll(pageable);
    }

    @PostMapping
    public void createCourse(@RequestBody CourseDTORequest request) {
        courseService.createCourse(request);
    }


    @GetMapping("/select")
    public List<CourseDTOResponseSelect> getAllSelect() {
        return courseService.getCourseSelect();
    }

    @GetMapping("/{id}")
    public CourseDTOResponseDetails getCourseById(@PathVariable Long id) {
        return courseService.getCourseDetailsById(id);
    }
}
