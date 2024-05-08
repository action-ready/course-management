package com.example.api.service.impl;


import com.example.api.entity.Course;
import com.example.api.entity.Schedule;
import com.example.api.entity.Semester;
import com.example.api.exception.GlobalException;
import com.example.api.payload.request.CourseDTORequest;
import com.example.api.payload.response.CourseDTOResponseDetails;
import com.example.api.payload.response.CourseDTOResponseSelect;
import com.example.api.payload.response.CourseResponse;
import com.example.api.repository.CourseRepository;
import com.example.api.repository.ScheduleRepository;
import com.example.api.repository.SemesterRepository;
import com.example.api.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final SemesterRepository semesterRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    @Transactional
    public void createCourse(CourseDTORequest request) {
        Course course = modelMapper.map(request, Course.class);
        Course courseSave = courseRepository.save(course);
        for (int i = 0; i < 4; i++) {
            Semester semester = new Semester();
            semester.setName("Kỳ " + (i + 1));
            semester.setCourse(courseSave);
            semesterRepository.save(semester);
        }
    }


    @Override
    public List<CourseDTOResponseSelect> getCourseSelect() {
        List<Course> courses = courseRepository.findAll();

        List<CourseDTOResponseSelect> responses = courses.stream()
                .map(course -> modelMapper.map(course, CourseDTOResponseSelect.class))
                .sorted(Comparator.comparing(CourseDTOResponseSelect::getId))
                .collect(Collectors.toList());

        return responses;
    }

    @Override
    public Page<CourseResponse> getAll(Pageable pageable) {
        Pageable firstPageWithTwoElements = PageRequest
                .of(pageable.getPageNumber(), 9, Sort.by("id").descending());

        Page<Course> coursePage = courseRepository.findAll(firstPageWithTwoElements);

        Page<CourseResponse> responses = coursePage.map(course -> modelMapper.map(course, CourseResponse.class));

        return responses;
    }

    @Override
    public CourseDTOResponseDetails getCourseDetailsById(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new GlobalException("Course not found"));


        CourseDTOResponseDetails responseDetails = modelMapper.map(course, CourseDTOResponseDetails.class);
        responseDetails.sortSemestersById();


        return responseDetails;
    }


}
