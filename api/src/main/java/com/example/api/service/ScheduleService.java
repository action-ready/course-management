package com.example.api.service;

import com.example.api.payload.request.ScheduleDTORequest;
import com.example.api.payload.request.ScheduleDTORequestUpdate;
import com.example.api.payload.response.ScheduleDTOResponse;
import com.example.api.payload.response.ScheduleWithClazzResponse;

import java.util.List;

public interface ScheduleService {

    void createSchedules(List<ScheduleDTORequest> requests, Long semesterId);

    void createSchedule(ScheduleDTORequest request, Long semesterId);

    List<ScheduleDTOResponse> getAllBySemesterId(Long semesterId);
    ScheduleDTOResponse getById(Long scheduleId);

    List<ScheduleWithClazzResponse> getScheduleByClazzId(Long clazzId);

    void updateScheduleById(Long id, ScheduleDTORequestUpdate requestUpdate, Long semesterId);

    void updateStatusBlockSchedule(Long id);
    void updateStatusActiveSchedule(Long id);

}
