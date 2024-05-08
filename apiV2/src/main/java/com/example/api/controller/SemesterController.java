package com.example.api.controller;


import com.example.api.entity.Semester;
import com.example.api.payload.request.SemesterDTORequest;
import com.example.api.payload.response.SemesterResponse;
import com.example.api.service.SemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SemesterController {

    private final SemesterService semesterService;

    @PostMapping("/{courseId}/semesters")
    public void createSemester(@RequestBody List<SemesterDTORequest> requests,
                               @PathVariable Long courseId) {
        semesterService.createSemester(requests,courseId);
    }

    @GetMapping("/course/{id}/semesters")
    public List<SemesterResponse> getAllByCourseId(@PathVariable Long id) {
        return semesterService.getAllByCourseId(id);
    }
}
