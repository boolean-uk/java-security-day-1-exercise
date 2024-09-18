package com.booleanuk.api.controller;

import com.booleanuk.api.model.Game;
import com.booleanuk.api.model.User;
import com.booleanuk.api.response.*;
import com.booleanuk.api.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserRepository users;

    @GetMapping
    public ResponseEntity<UserListResponse> getAll() {
        UserListResponse userListResponse = new UserListResponse();
        userListResponse.set(this.users.findAll());
        return new ResponseEntity<>(userListResponse, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getOne(@PathVariable(name = "id") int id) {
        User toReturn = this.users.findById(id)
                .orElse(
                        null
                );

        if (toReturn == null) {
            ErrorResponse errorResponse = new ErrorResponse("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        UserResponse userResponse = new UserResponse();
        userResponse.set(toReturn);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody User toAdd) {
        UserResponse userResponse = new UserResponse();
        userResponse.set(this.users.save(toAdd));
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> update(@PathVariable(name = "id") int id, @RequestBody User newData) {
        User toUpdate = this.users.findById(id)
                .orElse(
                        null
                );

        // 404 not found
        if (toUpdate == null) {
            ErrorResponse error = new ErrorResponse("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        toUpdate.setUsername(newData.getUsername());
        toUpdate.setEmail(newData.getEmail());
        toUpdate.setName(newData.getName());

        UserResponse userResponse = new UserResponse();
        userResponse.set(this.users.save(toUpdate));

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable(name = "id") int id) {
        User toDelete = this.users.findById(id)
                .orElse(
                        null
                );

        // 404 not found
        if (toDelete == null) {
            ErrorResponse error = new ErrorResponse("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        this.users.delete(toDelete);

        UserResponse userResponse = new UserResponse();
        userResponse.set(toDelete);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

}
