package com.example.api.service.impl;


import com.example.api.entity.*;
import com.example.api.exception.GlobalException;
import com.example.api.payload.request.GradesDTORequest;
import com.example.api.payload.response.StudentWithGradeDTO;
import com.example.api.repository.GradesRepository;
import com.example.api.repository.ScheduleRepository;
import com.example.api.repository.SemesterRepository;
import com.example.api.repository.StudentRepository;
import com.example.api.service.GradesService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GradesServiceImpl implements GradesService {

    private final GradesRepository gradesRepository;
    private final StudentRepository studentRepository;
    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;
    private final SemesterRepository semesterRepository;


    @Override
    public void createGrades(List<GradesDTORequest> requests) {
//        Set<Schedule> schedulesToSave = new HashSet<>();
        boolean checkStatusNull = false;
        for (GradesDTORequest gradesDTO : requests) {
            if(gradesDTO.getId() == null) {
                Grades grade = modelMapper.map(gradesDTO, Grades.class);
                Grades gradesSave = gradesRepository.save(grade);
//                if (gradesSave.getGrade() == null) {
//                    checkStatusNull = true;
//                }
            }else{
                Grades grade = gradesRepository.findById(gradesDTO.getId()).orElseThrow();
                grade.setGrade(gradesDTO.getGrade());
                gradesRepository.save(grade);
            }


            Optional<Schedule> schedule = Optional.ofNullable(scheduleRepository.findById(gradesDTO.getScheduleId()))
                    .orElseThrow(() -> new GlobalException("Schedule not found"));
            schedule.get().setStatus(Schedule.Status.UNFINISHED);
//
//            schedule = Optional.ofNullable(scheduleRepository.findById(gradesDTO.getScheduleId())
//                    .orElseThrow(() -> new RuntimeException("Schedule not found")));
//
//            schedule.get().setStatus(Schedule.Status.BLOCK);
//            schedulesToSave.add(schedule.get());
//
//            Optional<Schedule> scheduleNext = scheduleRepository.findBySemesterAndId(schedule.get().getSemester(), schedule.get().getId() + 1);
//            if (scheduleNext.isPresent()) {
//                scheduleNext.get().setStatus(Schedule.Status.ACTIVE);
//                schedulesToSave.add(scheduleNext.get());
//            } else {
//                scheduleNext = Optional.ofNullable(scheduleRepository.findById(schedule.get().getId() + 1)
//                        .orElseThrow(() -> new RuntimeException("Next schedule not found")));
//                scheduleNext.get().setStatus(Schedule.Status.ACTIVE);
//                schedulesToSave.add(scheduleNext.get());
//            }
        }



//        if (checkStatusNull) {
//            schedule.get().setStatus(Schedule.Status.UNFINISHED);
//            scheduleRepository.save(schedule.get());
//        }
    }

    @Override
    public List<StudentWithGradeDTO> findAllByClazzIdAndScheduleId(Long clazzId, Long scheduleId) {


        return gradesRepository.findAllByClazzIdAndScheduleId(clazzId, scheduleId);
    }


}
