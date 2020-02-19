package com.holubinka.services;

import com.holubinka.controller.model.UserExt;
import com.holubinka.model.Color;
import com.holubinka.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> create(UserExt user);

    Optional<List<User>> getAll();

    Optional<List<User>> getUsersByAge(Integer age);

    Optional<List<User>> getUsersByArticleColor(String color);

    Optional<List<User>> getDistinctUsersByName();

}
