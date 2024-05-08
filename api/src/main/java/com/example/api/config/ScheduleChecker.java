package com.example.api.config;

import com.example.api.entity.Schedule;
import com.example.api.payload.response.ScheduleCheckStartDate;
import com.example.api.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Optional;

@Configuration
@EnableScheduling
public class ScheduleChecker {

    @Autowired
    private ScheduleRepository scheduleRepository;


    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Ho_Chi_Minh")
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




