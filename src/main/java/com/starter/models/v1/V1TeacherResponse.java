package com.starter.models.v1;


import com.starter.models.entities.Teacher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*Us responding !! DEFINES HOW IT WILL LOOK*/

@Data //sets up getters and setters
@Builder //produces complex builder APIs for the following class - automatically implements the code required to have your class be instantiable with complex object-method combos
@NoArgsConstructor //no arguments required
@AllArgsConstructor
public class V1TeacherResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private int age;

    public static V1TeacherResponse fromTeacher(Teacher teacher) { //function is used in the controller to retrieve data for get request
        return V1TeacherResponse.builder()
                .id(teacher.getId())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .age(teacher.getAge())
                .build();
    }

}
