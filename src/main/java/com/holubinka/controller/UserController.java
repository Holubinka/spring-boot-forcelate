package com.holubinka.controller;

import com.holubinka.controller.model.UserExt;
import com.holubinka.model.Color;
import com.holubinka.model.User;
import com.holubinka.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll().orElseGet(Collections::emptyList));
    }

    @PostMapping(value = "/users")
    public ResponseEntity<User> create(@Valid @RequestBody UserExt userExt) {
        return userService.create(userExt)
                .map(u -> ResponseEntity.created(getUri("users", u.getId())).body(u))
                .orElseGet(ResponseEntity.status(HttpStatus.CONFLICT)::build);
    }

    @GetMapping(value = "/users/{years}")
    public ResponseEntity<List<User>> getUsersByAge(@PathVariable Integer years) {
        return userService.getUsersByAge(years)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.status(HttpStatus.NOT_FOUND)::build);
    }

    @GetMapping(value = "/users/article/{color}")
    public ResponseEntity<List<User>> getUsersByArticleColor(@PathVariable String color) {
        return userService.getUsersByArticleColor(color)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.status(HttpStatus.NOT_FOUND)::build);
    }

    @GetMapping(value = "/users/article-great-than-3")
    public ResponseEntity<List<User>> getDistinctUserByName() {
        return userService.getDistinctUsersByName()
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.status(HttpStatus.NOT_FOUND)::build);
    }

    private URI getUri(String uri, Long id) {
        return URI.create(String.format("/%s/%s", uri, id));
    }
}
