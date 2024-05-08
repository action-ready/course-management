package com.example.api.service;

import com.example.api.payload.request.ClazzDTORequest;
import com.example.api.payload.request.ClazzDTORequestImport;
import com.example.api.payload.response.ClazzResponse;
import com.example.api.payload.response.ClazzResponseDetails;

import java.util.List;

public interface ClazzService {


    void createClazz(ClazzDTORequest request);

    void updateClazzImport(Long clazzId, ClazzDTORequestImport request);

    List<ClazzResponse> getAll();

    ClazzResponseDetails getClazzById(Long clazzId);

    List<ClazzResponse> getAllByTeacherId(Long teacherId);

    Long getClazzIdByStudentId(Long studentId);
}
