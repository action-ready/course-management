package com.example.api.payload.response;


import com.example.api.entity.Clazz;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponse {

    private Long id;
    private String fullName;
    private List<Clazz> clazzes;
    private int numberOfClazzes;
    @Data
    static class Clazz {
        private Long id;
    }

    public void setNumberOfClazzes() {
        this.numberOfClazzes = clazzes.size();
    }

}
