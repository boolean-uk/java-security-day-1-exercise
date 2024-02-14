package com.booleanuk.api.controller;

import com.booleanuk.api.exception.ApiException;
import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.api.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    UserRepository repository;

    @GetMapping
    public ResponseEntity<Response<List<User>>> getAll() {
        return ResponseEntity.ok(new Response<>(this.repository.findAll()));
    }

    @PostMapping
    public ResponseEntity<Response<User>> create(@RequestBody User user) {
        if (user.getUsername() == null || user.getEmail() == null || user.getDob() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "bad request");
        }
        User createdUser = this.repository.save(user);
        createdUser.setBorrowedGames(new ArrayList<>());
        return new ResponseEntity<>(new Response<>(createdUser), HttpStatus.CREATED);
    }
}
