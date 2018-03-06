package com.starter.controllers;

import com.starter.models.v1.V1UserResponse;
import com.starter.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequestMapping("/api/users")
@RestController
public class V1UserController {

    private final UserRepository userRepository;

    @Autowired
    public V1UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public List<V1UserResponse> getUsers() {
        return userRepository.findAllBy().stream()
                .map(V1UserResponse::fromUser)
                .collect(toList());
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public V1UserResponse getUser(@PathVariable("id") @NotNull Long id) {
        return userRepository.findById(id)
                .map(V1UserResponse::fromUser)
                .orElseThrow(IllegalArgumentException::new);
    }



}
