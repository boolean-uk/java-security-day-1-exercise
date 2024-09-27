package com.booleanuk.api.user;

import com.booleanuk.api.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.Error;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Response> getAll() {
        return new ResponseEntity<>(new UserListResponse(this.userRepository.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getUser(@PathVariable int id) {
        User user = this.userRepository
                .findById(id)
                .orElse(null);
        if (user == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("User not found")), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new UserResponse(user), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Response> createUser(@RequestBody User user) {

        if(user.getName().isEmpty()){
            return new ResponseEntity<>(new ErrorResponse(new Error("Bad Request")), HttpStatus.BAD_REQUEST);
        }

        this.userRepository.save(user);
        return new ResponseEntity<>(new UserResponse(user), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteGame(@PathVariable int id) {
        User deleted = this.userRepository
                .findById(id)
                .orElse(null);

        if (deleted == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("User not found")), HttpStatus.NOT_FOUND);
        }
        this.userRepository.delete(deleted);
        return new ResponseEntity<>(new UserResponse(deleted), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateGame (@PathVariable int id, @RequestBody User user) {

        User userToUpdate = this.userRepository
                .findById(id)
                .orElse(null);

        if (userToUpdate == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("User not found")), HttpStatus.NOT_FOUND);
        }

        if(user.getName().isEmpty()){
            return new ResponseEntity<>(new ErrorResponse(new Error("Bad Request")), HttpStatus.BAD_REQUEST);
        }

        userToUpdate.setName(user.getName());

        this.userRepository.save(userToUpdate);
        return new ResponseEntity<>(new UserResponse(userToUpdate), HttpStatus.OK);
    }

}
