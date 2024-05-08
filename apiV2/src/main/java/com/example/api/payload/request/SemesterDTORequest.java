package com.example.api.payload.request;


import com.example.api.entity.Schedule;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SemesterDTORequest {

    private Set<Schedule> schedules;

    @Data
    static class Schedule {

        private String name;
        private String description;

    }
}
