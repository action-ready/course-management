package com.example.api.service;

import com.example.api.payload.request.ScheduleDTORequest;
import com.example.api.payload.request.SemesterDTORequest;
import com.example.api.payload.response.SemesterResponse;

import java.util.List;
import java.util.Set;

public interface SemesterService {

    void createSemester(List<SemesterDTORequest> requests, Long courseId);
    void createSemesterV2(List<ScheduleDTORequest> requests, Long semesterId);

    List<SemesterResponse> getAllByCourseId(Long courseId);


}
