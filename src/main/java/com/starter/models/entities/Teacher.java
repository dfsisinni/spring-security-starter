package com.starter.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*Entity class is required for each table created*/

@Data //sets up getters and setters, etc
@Builder//need this here so getters setters are instantiated for requests
@Entity //class can be mapped to a table
@Table(name = "teachers") //specifying table we are working with
@NoArgsConstructor //can create a teacher object without arguments
@AllArgsConstructor
public class Teacher {

    @Id //every database requires an id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 0, max = 100)
    private String firstName;

    @NotNull
    @Size(min = 0, max = 100)
    private String lastName;

    @NotNull
    private int age;
}
