package com.example.api.controller;


import com.example.api.payload.request.ClazzDTORequest;
import com.example.api.payload.request.ClazzDTORequestImport;
import com.example.api.payload.response.ClazzResponse;
import com.example.api.payload.response.ClazzResponseDetails;
import com.example.api.service.ClazzService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ClazzController {

    private final ClazzService clazzService;

    @GetMapping("/clazzs")
    public List<ClazzResponse> getAll() {
        return clazzService.getAll();
    }

    @GetMapping("/clazzs/{id}")
    public ClazzResponseDetails getClazzById(@PathVariable Long id) {
        return clazzService.getClazzById(id);
    }


    @PostMapping("/clazzs")
    public void createClazz(@RequestBody ClazzDTORequest request) {
        clazzService.createClazz(request);
    }

    @PutMapping("/clazzs/{id}")
    public void updateClazzImport(@RequestBody ClazzDTORequestImport request, @PathVariable Long id) {
        clazzService.updateClazzImport(id, request);
    }

    @GetMapping("/teacher/{teacherId}/clazzs")
    public List<ClazzResponse> getClazzByTeacherId(@PathVariable Long teacherId) {
        return clazzService.getAllByTeacherId(teacherId);
    }

    @GetMapping("/studentId/{studentId}/clazz")
    public Long getClazzIdByStudentId(@PathVariable Long studentId) {
        return clazzService.getClazzIdByStudentId(studentId);
    }
}
