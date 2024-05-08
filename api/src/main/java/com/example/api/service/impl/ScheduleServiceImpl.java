package com.example.api.service.impl;


import com.example.api.entity.Schedule;
import com.example.api.entity.Semester;
import com.example.api.exception.GlobalException;
import com.example.api.payload.request.ScheduleDTORequest;
import com.example.api.payload.request.ScheduleDTORequestUpdate;
import com.example.api.payload.response.CourseDTOResponseDetails;
import com.example.api.payload.response.ScheduleDTOResponse;
import com.example.api.payload.response.ScheduleWithClazzResponse;
import com.example.api.repository.ScheduleRepository;
import com.example.api.repository.SemesterRepository;
import com.example.api.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final SemesterRepository semesterRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createSchedules(List<ScheduleDTORequest> requests, Long semesterId) {

        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new GlobalException("Semester not found"));
        Set<Schedule> schedules = new HashSet<>();
        for (ScheduleDTORequest x : requests) {
            Schedule schedule = modelMapper.map(x, Schedule.class);
            semester.setDescription(x.getDescription());
            schedule.setSemester(semester);
            schedules.add(schedule);
        }
    }

    @Override
    @Transactional
    public void createSchedule(ScheduleDTORequest request, Long semesterId) {
        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new GlobalException("Semester not found"));
        semester.setDescription(request.getDetailsSemester());
        semesterRepository.save(semester);


        Schedule schedule = modelMapper.map(request, Schedule.class);
        schedule.setSemester(semester);

        scheduleRepository.save(schedule);
    }

    @Override
    public List<ScheduleDTOResponse> getAllBySemesterId(Long semesterId) {
        List<Schedule> schedules = scheduleRepository.findBySemesterId(semesterId);

        List<ScheduleDTOResponse> responses = schedules.stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleDTOResponse.class))
                .collect(Collectors.toList());

        return responses;
    }

    @Override
    public ScheduleDTOResponse getById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new GlobalException("schedule not found"));
        ScheduleDTOResponse response = modelMapper.map(schedule, ScheduleDTOResponse.class);

        return response;
    }


    @Override
    public List<ScheduleWithClazzResponse> getScheduleByClazzId(Long clazzId) {

        List<ScheduleWithClazzResponse> responses = scheduleRepository.findScheduleByClazzId(clazzId);

        return responses;
    }

    @Override
    public void updateScheduleById(Long id, ScheduleDTORequestUpdate requestUpdate, Long semesterId) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new GlobalException("Schedule not found"));

        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new GlobalException("Semester not found"));
        schedule.setName(requestUpdate.getName());
        schedule.setSemester(semester);
        schedule.setDescription(requestUpdate.getDescription());

        scheduleRepository.save(schedule);
    }

    @Override
    public void updateStatusBlockSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new GlobalException("Schedule not found"));
        schedule.setStatus(Schedule.Status.BLOCK);


        scheduleRepository.save(schedule);
    }

    @Override
    public void updateStatusActiveSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new GlobalException("Schedule not found"));
        schedule.setStatus(Schedule.Status.ACTIVE);
        Schedule schedule1 = scheduleRepository.findById(schedule.getId()+1).get();
        schedule1.setStatus(Schedule.Status.COMING_SOON);
        scheduleRepository.save(schedule1);
        scheduleRepository.save(schedule);
    }
}
