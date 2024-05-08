package com.example.api.payload.response;

import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class CourseDTOResponseDetails {

    private Long id;

    private String name;
    private Date startDate;
    private Date endDate;
    private Set<Semester> semesters;
    private Integer price;
    private String imageUrl;
    private String description;
    private String language;

    public void sortSemestersById() {
        this.semesters = this.semesters.stream()
                .sorted(Comparator.comparing(Semester::getId))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Data
   public static class Semester {
        private Long id;
        private String name;
        private String description;
        private Set<Schedule> schedules;


        @Data
        public  static class Schedule {
            private Long id;

            private String name;
            private String description;
            private String startDate;
            private String endDate;
            private Integer numberOfSessions;

        }
    }
}
