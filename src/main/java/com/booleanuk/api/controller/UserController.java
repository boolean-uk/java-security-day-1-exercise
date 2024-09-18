package com.booleanuk.api.controller;

import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private final UserRepository repository;

    public UserController(UserRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getOne(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.repository.findById(id).orElseThrow());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        return new ResponseEntity<>(this.repository.save(user),HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateAuthor(@PathVariable("id") Integer id,
                                               @RequestBody User user) {
        User userToUpdate = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existing user with that ID found")
        );
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        return new ResponseEntity<>(this.repository.save(userToUpdate),
                HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> deleteAuthor(@PathVariable("id") Integer id) {
        User userToDelete = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No user with that ID were found")
        );
        this.repository.delete(userToDelete);
        return ResponseEntity.ok(userToDelete);
    }


}
