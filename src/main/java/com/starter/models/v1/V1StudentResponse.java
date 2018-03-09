package com.starter.models.v1;

import com.starter.models.entities.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class V1StudentResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private int age;

    private List<V1CourseResponse> courses;

    public static V1StudentResponse fromStudent(Student student) {
        return V1StudentResponse.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .age(student.getAge())
                .courses(student.getCourses().stream()
                    .map(V1CourseResponse::fromCourse)
                    .collect(Collectors.toList()))
                .build();
    }

}
