package com.example.api.payload.response;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CourseResponse {

    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private Integer price;
    private String imageUrl;
    private String description;
    private String language;
    private String studyTime;


}
