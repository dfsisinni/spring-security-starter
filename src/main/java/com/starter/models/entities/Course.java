package com.starter.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "courses")
@NoArgsConstructor
public class Course {

    @Id
    @NotNull
    @Size(min = 1, max = 10)
    private String id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

}
