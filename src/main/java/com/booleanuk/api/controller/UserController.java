package com.booleanuk.api.controller;

import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.response.UserListResponse;
import com.booleanuk.api.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired 
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<UserListResponse> getAllUsers() {
        // Success response with list of users, can be empty
        UserListResponse userListResponse = new UserListResponse();
        userListResponse.set(this.userRepository.findAll());
        return ResponseEntity.ok(userListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getUserById(@PathVariable int id) {
        User user = this.userRepository.findById(id).orElse(null);
        // 404 Not found
        if (user == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("User not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        // Success response of user with specific ID
        UserResponse userResponse = new UserResponse();
        userResponse.set(user);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createUser(@RequestBody User user) {
        // 400 Bad request
        if (user.getFirstName() == null || user.getLastName() == null || user.getBirthday() == null ||
                user.getPhone() == null || user.getEmail() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request, please check all fields are correct");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        // Success response of created user
        UserResponse userResponse = new UserResponse();
        userResponse.set(this.userRepository.save(user));
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateUser(@PathVariable int id, @RequestBody User user) {
        User userToUpdate = this.userRepository.findById(id).orElse(null);
        // 404 Not found
        if (userToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("User not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        // 400 Bad request
        if (user.getFirstName() == null || user.getLastName() == null || user.getBirthday() == null ||
                user.getPhone() == null || user.getEmail() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request, please check all fields are correct");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        // Update fields
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setBirthday(user.getBirthday());
        userToUpdate.setPhone(user.getPhone());
        userToUpdate.setEmail(user.getEmail());
        // Success response of updated user
        UserResponse userResponse = new UserResponse();
        userResponse.set(this.userRepository.save(userToUpdate));
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteUser(@PathVariable int id) {
        User userToDelete = this.userRepository.findById(id).orElse(null);
        // 404 Not found
        if (userToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("User not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        // Deletes user with specified ID
        this.userRepository.delete(userToDelete);
        // Success response of deleted user
        UserResponse userResponse = new UserResponse();
        userResponse.set(userToDelete);
        return ResponseEntity.ok(userResponse);
    }

}
