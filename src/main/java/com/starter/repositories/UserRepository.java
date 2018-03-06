package com.starter.repositories;

import com.starter.models.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

    List<User> findAllBy();

}
