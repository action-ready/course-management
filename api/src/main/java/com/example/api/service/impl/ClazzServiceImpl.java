package com.example.api.service.impl;


import com.example.api.entity.Clazz;
import com.example.api.entity.Course;
import com.example.api.entity.Schedule;
import com.example.api.entity.Student;
import com.example.api.exception.GlobalException;
import com.example.api.payload.request.ClazzDTORequest;
import com.example.api.payload.request.ClazzDTORequestImport;
import com.example.api.payload.response.ClazzResponse;
import com.example.api.payload.response.ClazzResponseDetails;
import com.example.api.payload.response.ScheduleCheckStartDate;
import com.example.api.repository.ClazzRepository;
import com.example.api.repository.ScheduleRepository;
import com.example.api.repository.StudentRepository;
import com.example.api.repository.TeacherRepository;
import com.example.api.service.ClazzService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClazzServiceImpl implements ClazzService {

    private final ClazzRepository clazzRepository;
    private final StudentRepository studentRepository;
    private final ScheduleRepository scheduleRepository;
    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createClazz(ClazzDTORequest request) {
        Clazz clazz = modelMapper.map(request, Clazz.class);

        String clazzCode = generateClazzCode();
        clazz.setStatus(Clazz.Status.ACTIVE);

        clazz.setClazzCode(clazzCode);
        Clazz clazzSave = clazzRepository.save(clazz);

        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = clazzSave.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (startDate.equals(currentDate)) {
            startChecking();
        }
    }

    @Override
    @Transactional
    public void updateClazzImport(Long clazzId, ClazzDTORequestImport request) {

        Clazz clazz = clazzRepository.findById(clazzId).orElseThrow(() -> new GlobalException("Clazz not found"));


        for (int i = 0; i < request.getStudentCode().size(); i++) {
            int index = i;
            Optional<Student> student = Optional.ofNullable(studentRepository.findByStudentCode(request.getStudentCode().get(index)).orElseThrow(() -> new GlobalException("Student not found at line " + (index + 1))));
            clazz.getStudents().add(student.get());
        }
        clazzRepository.save(clazz);

    }

    @Override
    public List<ClazzResponse> getAll() {

        List<Clazz> clazzes = clazzRepository.findAll(Sort.by("id").descending());

        List<ClazzResponse> responses = clazzes.stream().map(clazz -> modelMapper.map(clazz, ClazzResponse.class)).collect(Collectors.toList());
        return responses;
    }

    @Override
    public ClazzResponseDetails getClazzById(Long clazzId) {
        Clazz clazz = clazzRepository.findById(clazzId).orElseThrow(() -> new GlobalException("Clazz not found"));

        ClazzResponseDetails responseDetails = modelMapper.map(clazz, ClazzResponseDetails.class);
        return responseDetails;
    }

    @Override
    public List<ClazzResponse> getAllByTeacherId(Long teacherId) {
        List<Clazz> clazzes = clazzRepository.findByTeacherId(teacherId);

        List<ClazzResponse> responses = clazzes.stream().map(clazz -> modelMapper.map(clazz, ClazzResponse.class)).sorted(Comparator.comparing(ClazzResponse::getId)).collect(Collectors.toList());

        return responses;
    }

    @Override
    public Long getClazzIdByStudentId(Long studentId) {
        return clazzRepository.getClazzIdByStudentId(studentId);
    }


    private String generateClazzCode() {
        String clazzCode = "";

        clazzCode += (char) (Math.random() * 26 + 'A');
        clazzCode += (char) (Math.random() * 26 + 'A');

        for (int i = 0; i < 5; i++) {
            clazzCode += (int) (Math.random() * 10);
        }
        return clazzCode;
    }

    public void startChecking() {
        List<ScheduleCheckStartDate> schedules = scheduleRepository.getAllCheckStartDate();
        for (ScheduleCheckStartDate schedule : schedules) {
            Optional<Schedule> scheduleUpdate = scheduleRepository.findById(schedule.getScheduleId());
            if (scheduleUpdate.isPresent()) {
                scheduleUpdate.get().setStatus(Schedule.Status.ACTIVE);
                scheduleRepository.save(scheduleUpdate.get());
            }
        }
    }
}
