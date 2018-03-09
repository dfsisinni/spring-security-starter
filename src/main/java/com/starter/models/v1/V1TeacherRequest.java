package com.starter.models.v1;

import com.starter.models.entities.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*requesting information from the client*/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class V1TeacherRequest {

    @NotNull
    @Size(min=1, max=100)
    private String firstName;

    @NotNull
    @Size(min=1, max=100)
    private String lastName;

    @NotNull
    private int age;

    public Teacher toTeacher() {
        return Teacher.builder()
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .build();
    }


}
