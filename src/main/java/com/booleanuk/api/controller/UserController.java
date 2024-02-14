package com.booleanuk.api.controller;

import com.booleanuk.api.model.User;
import com.booleanuk.api.model.VideoGame;
import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.response.ErrorResponse;
import com.booleanuk.response.Response;
import com.booleanuk.response.UserListResponse;
import com.booleanuk.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<UserListResponse> getAllUsers() {
        List<User> authors = this.userRepository.findAll();
        UserListResponse userListResponse = new UserListResponse();
        userListResponse.set(authors);
        return ResponseEntity.ok(userListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getUserById(@PathVariable int id) {
        User returnUser = this.userRepository.findById(id).orElse(null);
        if (returnUser == null ) {
            ErrorResponse error = new ErrorResponse();
            error.set("No users matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        UserResponse userResponse = new UserResponse();
        userResponse.set(returnUser);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createUser(@RequestBody User user) {
        if (user.getName() == null || user.getPhone() == null || user.getEmail() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create the user, please check all required fields");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        User createdUser = this.userRepository.save(user);
        createdUser.setVideoGames(new ArrayList<>());

        UserResponse userResponse = new UserResponse();
        userResponse.set(createdUser);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateUser(@PathVariable int id, @RequestBody User user) {
        if (user.getName() == null || user.getPhone() == null || user.getEmail() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not update the user's details, please check all required fields");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        User userToUpdate = this.userRepository.findById(id).orElse(null);

        if(userToUpdate == null ) {
            ErrorResponse error = new ErrorResponse();
            error.set("No users matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        userToUpdate.setName(user.getName());
        userToUpdate.setPhone(user.getPhone());
        userToUpdate.setEmail(user.getEmail());

        UserResponse userResponse = new UserResponse();
        userResponse.set(userToUpdate);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteUser(@PathVariable int id) {
        User userToDelete = this.userRepository.findById(id).orElse(null);

        if(userToDelete == null ) {
            ErrorResponse error = new ErrorResponse();
            error.set("No users matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        this.userRepository.delete(userToDelete);
        userToDelete.setVideoGames(new ArrayList<VideoGame>());

        UserResponse userResponse = new UserResponse();
        userResponse.set(userToDelete);
        return ResponseEntity.ok(userResponse);
    }

}
