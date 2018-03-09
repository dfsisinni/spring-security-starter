package com.starter.controllers;

import com.starter.models.v1.V1TeacherRequest;
import com.starter.models.v1.V1TeacherResponse;
import com.starter.repositories.TeacherRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping(value = "/api/teachers") //where the enpoint can be accessed
@RestController //defining that this is a rest controller
public class V1TeacherController {

    private final TeacherRepository teacherRepository;

    @Autowired
    public V1TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public List<V1TeacherResponse> getTeachers() {
        return teacherRepository.findAllBy().stream()
                .map(V1TeacherResponse::fromTeacher)
                .collect(Collectors.toList());
    }

    @Transactional
    @RequestMapping(method = RequestMethod.PUT)
    public V1TeacherResponse createTeacher(@RequestBody @Valid @NotNull V1TeacherRequest teacherRequest) {
        val teacher = teacherRequest.toTeacher();
        return V1TeacherResponse.fromTeacher(teacherRepository.save(teacher));
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public V1TeacherResponse updateTeacher(@PathVariable("id") @NotNull Long id, @RequestBody @Valid @NotNull V1TeacherRequest request) {
        val toUpdate = teacherRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        toUpdate.setAge(request.getAge());
        toUpdate.setFirstName(request.getFirstName());
        toUpdate.setLastName(request.getLastName());

        return V1TeacherResponse.fromTeacher(teacherRepository.save(toUpdate));
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteTeacher(@PathVariable("id") @NotNull Long id) {
        val toDelete = teacherRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        teacherRepository.delete(toDelete);
    }
}
