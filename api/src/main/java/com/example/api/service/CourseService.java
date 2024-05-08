package com.example.api.service;

import com.example.api.payload.request.CourseDTORequest;
import com.example.api.payload.response.CourseDTOResponseDetails;
import com.example.api.payload.response.CourseDTOResponseSelect;
import com.example.api.payload.response.CourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {


    void createCourse(CourseDTORequest request);


    List<CourseDTOResponseSelect> getCourseSelect();

    Page<CourseResponse> getAll(Pageable pageable);

    CourseDTOResponseDetails getCourseDetailsById(Long id);

}
