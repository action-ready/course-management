package com.example.api.service;

import com.example.api.payload.request.TeacherDTORequest;
import com.example.api.payload.response.TeacherResponse;
import org.springframework.data.domain.Page;

public interface TeacherService {


    void createTeacher(TeacherDTORequest request);

    Page<TeacherResponse> getAll( int page );
}
