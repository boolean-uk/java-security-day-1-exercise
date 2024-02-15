package com.booleanuk.api.controller;

import com.booleanuk.api.model.Game;
import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.api.response.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
@RequestMapping("users")
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<CustomResponse> getAllUsers() {

        if (userRepository.count() < 1) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("No data found"));
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(errResponse);
        }


        CustomResponse response = new CustomResponse("success", this.userRepository.findAll());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getUserByID(@PathVariable int id) {
        if (!userRepository.existsById(id)) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("Id is not in database!"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }
        User user = this.userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        CustomResponse res = new CustomResponse("Success", user);
        return ResponseEntity.ok(res);
    }


    @PostMapping
    public ResponseEntity<CustomResponse> createUser(@RequestBody User user) {

        if (user.getName() == null || user.getAge() == 0 || user.getEmail() == null || user.getPhone() == null) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("Check if all fields are correct!"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }
        CustomResponse res = new CustomResponse("success", this.userRepository.save(user));

        return ResponseEntity.ok(res);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteUserByID(@PathVariable int id) {

        if (!userRepository.existsById(id)) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("Id is not in database!"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }

        User user = this.userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        this.userRepository.delete(user);
        user.setGames(new ArrayList<Game>());

        CustomResponse res = new CustomResponse("success", user);
        return ResponseEntity.ok(res);


    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateUser(@PathVariable int id, @RequestBody User user) {


        User prevuser = this.userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found!"));
        User userToUpdate = this.userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));

        if (user.getName() == null) {
            userToUpdate.setName(prevuser.getName());
        }
        else {
            userToUpdate.setName(user.getName());
        }
        if (user.getAge() == 0) {
            userToUpdate.setAge(prevuser.getAge());
        }
        else {
            userToUpdate.setAge(user.getAge());
        }
        if (user.getEmail() == null) {
            userToUpdate.setEmail(prevuser.getEmail());
        }
        else {
            userToUpdate.setEmail(user.getEmail());
        }
        if (user.getPhone() == null) {
            userToUpdate.setPhone(prevuser.getPhone());
        }
        else {
            userToUpdate.setPhone(user.getPhone());
        }


        CustomResponse res = new CustomResponse("success", this.userRepository.save(userToUpdate));

        return ResponseEntity.ok(res);
    }

}
