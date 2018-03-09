package com.starter.models.v1;

import com.starter.models.entities.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class V1CourseResponse {

    private String id;

    private String name;

    public static V1CourseResponse fromCourse(Course course) {
        return V1CourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .build();
    }

}
