package com.example.api.service;

import com.example.api.payload.request.GradesDTORequest;
import com.example.api.payload.response.StudentWithGradeDTO;

import java.util.List;
import java.util.Set;

public interface GradesService {


    void createGrades(List<GradesDTORequest> requests);
    List<StudentWithGradeDTO> findAllByClazzIdAndScheduleId(Long clazzId, Long scheduleId);
}
