package com.example.api.controller;


import com.example.api.payload.request.ScheduleDTORequest;
import com.example.api.payload.request.ScheduleDTORequestUpdate;
import com.example.api.payload.response.ScheduleDTOResponse;
import com.example.api.payload.response.ScheduleWithClazzResponse;
import com.example.api.repository.ScheduleRepository;
import com.example.api.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ScheduleController {

    private final ScheduleService scheduleService;


    @PostMapping("/{id}/schedules")
    public void createSchedule(@RequestBody List<ScheduleDTORequest> requestList,
                               @PathVariable Long id) {
        scheduleService.createSchedules(requestList, id);
    }

    @PostMapping("/{id}/schedule")
    public void createSchedule(@RequestBody ScheduleDTORequest request,
                               @PathVariable Long id) {
        scheduleService.createSchedule(request, id);
    }

    @PutMapping("/semester/{semesterId}/{id}")
    public void updateScheduleById(@PathVariable Long id,  @PathVariable Long semesterId,
                                   @RequestBody ScheduleDTORequestUpdate requestUpdate) {
        scheduleService.updateScheduleById(id, requestUpdate,semesterId);
    }

    @GetMapping("/semester/{id}/schedules")
    public List<ScheduleDTOResponse> getAllBySemesterId(@PathVariable Long id) {
        return scheduleService.getAllBySemesterId(id);
    }

    @GetMapping("/schedule/{id}")
    public ScheduleDTOResponse getById(@PathVariable Long id) {
        return scheduleService.getById(id);
    }

    @GetMapping("/clazz/{id}/schedules")
    public List<ScheduleWithClazzResponse> getScheduleByClazzId(@PathVariable Long id) {
        return scheduleService.getScheduleByClazzId(id);
    }

    @PutMapping("/schedules/{id}/block")
    public void updateBlockStatus(@PathVariable Long id) {
        scheduleService.updateStatusBlockSchedule(id);
    }
    @PutMapping("/schedules/{id}/active")
    public void updateBActiveStatus(@PathVariable Long id) {
        scheduleService.updateStatusActiveSchedule(id);
    }
}
