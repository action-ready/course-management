package com.example.api.controller;


import com.example.api.payload.request.GradesDTORequest;
import com.example.api.payload.response.StudentWithGradeDTO;
import com.example.api.repository.GradesRepository;
import com.example.api.service.GradesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class GradesController {

    private final GradesService gradesService;

    @PostMapping("/grades")
    public void createGrades(@RequestBody List<GradesDTORequest> requests) {
        gradesService.createGrades(requests);
    }


    @GetMapping("/clazz/{clazzId}/schedule/{scheduleId}/grades")
    public List<?> getByClazzAndSchedule(@PathVariable Long clazzId,
                                                @PathVariable Long scheduleId) {
        return gradesService.findAllByClazzIdAndScheduleId(clazzId, scheduleId);
    }
}
