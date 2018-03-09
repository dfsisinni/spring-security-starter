package com.starter.controllers;

import com.starter.models.v1.V1StudentResponse;
import com.starter.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/*CONTROLS THE EFFECTS OF ACCESING THE DEFINED END POINT*/

@RequestMapping(value = "/api/students")
@RestController
public class V1StudentController {

    private final StudentRepository studentRepository;

    @Autowired
    public V1StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public List<V1StudentResponse> getStudents() {
        return studentRepository.findAllBy().stream()
                .map(V1StudentResponse::fromStudent)
                .collect(Collectors.toList());
    }


}
