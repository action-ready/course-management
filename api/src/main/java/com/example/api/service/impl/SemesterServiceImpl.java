package com.example.api.service.impl;


import com.example.api.entity.Course;
import com.example.api.entity.Schedule;
import com.example.api.entity.Semester;
import com.example.api.exception.GlobalException;
import com.example.api.payload.request.ScheduleDTORequest;
import com.example.api.payload.request.SemesterDTORequest;
import com.example.api.payload.response.SemesterResponse;
import com.example.api.repository.CourseRepository;
import com.example.api.repository.ScheduleRepository;
import com.example.api.repository.SemesterRepository;
import com.example.api.service.SemesterService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SemesterServiceImpl implements SemesterService {

    private final ModelMapper modelMapper;
    private final SemesterRepository semesterRepository;
    private final ScheduleRepository scheduleRepository;
    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public void createSemester(List<SemesterDTORequest> requests, Long courseId) {
//        Course course = courseRepository.findById(courseId)
//                .orElseThrow(() -> new GlobalException("Course not found"));
//
//
//        Set<Schedule> schedules = new HashSet<>();
//
//        for (SemesterDTORequest request : requests) {
//            Semester semester = modelMapper.map(request, Semester.class);
//            semester.setName("Kỳ " +( course.getSemesters().size() + 1));
//            course.getSemesters().add(semester);
//            semester.setCourse(course);
//            Semester semesterSave = semesterRepository.save(semester);
//
//            for (Schedule schedule : semesterSave.getSchedules()) {
//                schedule.setSemester(semesterSave);
//                schedules.add(schedule);
//            }
//
//        }
//        scheduleRepository.saveAll(schedules);
    }

    @Override
    public void createSemesterV2(List<ScheduleDTORequest> requests, Long semesterId) {

        Semester semester = semesterRepository.findById(semesterId).orElseThrow();

        Set<Schedule> schedules = new HashSet<>();
        for(ScheduleDTORequest x : requests) {
            Schedule schedule = modelMapper.map(x, Schedule.class);
            schedule.setSemester(semester);
            schedules.add(schedule);
        }

    }

    @Override
    public List<SemesterResponse> getAllByCourseId(Long courseId) {

        List<Semester> semesters = semesterRepository.findByCourseId(courseId);

       List<SemesterResponse> responses = semesters.stream()
               .map(semester -> modelMapper.map(semester, SemesterResponse.class))
               .collect(Collectors.toList());

        return responses;
    }


}
