package com.booleanuk.api.controller;

import com.booleanuk.api.model.VideoGame;
import com.booleanuk.api.model.Rent;
import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.VideoGameRepository;
import com.booleanuk.api.repository.RentRepository;
import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.api.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VideoGameRepository gameRepository;

    @Autowired
    private RentRepository rentRepository;

    @GetMapping
    public ResponseEntity<UserListResponse> getAllUsers() {
        UserListResponse userListResponse = new UserListResponse();
        userListResponse.set(this.userRepository.findAll());
        return ResponseEntity.ok(userListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getUserById(@PathVariable int id) {
        User user = this.userRepository.findById(id).orElse(null);
        if (user == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        UserResponse userResponse = new UserResponse();
        userResponse.set(user);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/{id}/rents")
    public ResponseEntity<Response<?>> getRents(@PathVariable int id) {
        User user = this.userRepository.findById(id).orElse(null);
        if (user == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        RentListResponse rentListResponse = new RentListResponse();
        rentListResponse.set(user.getRents());
        return ResponseEntity.ok(rentListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createUser(@RequestBody User user) {
        //Check the fields
        if(user.getName().isEmpty() || user.getEmail().isEmpty()|| user.getPhone().isEmpty()){
            ErrorResponse error = new ErrorResponse();
            error.set("Fill in required fields");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        this.userRepository.save(user);

        UserResponse userResponse = new UserResponse();
        userResponse.set(user);
        return ResponseEntity.ok(userResponse);
    }

    //Create rent
    @PostMapping("{userId}/games/{gameId}")
    public ResponseEntity<Response<?>> createRent(@PathVariable(name="userId") Integer userId,
                                                  @PathVariable(name="gameId") Integer gameId,
                                                  @RequestBody Rent rent) {
        User user = this.userRepository.findById(userId).orElse(null);
        if (user ==null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        VideoGame game = this.gameRepository.findById(gameId).orElse(null);
        if (game ==null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        //Set update to right now
        LocalDateTime currentDateTime = LocalDateTime.now();
        rent.setCreatedAt(currentDateTime);
        game.setBorrowed(true);
        rent.setUser(user);
        rent.setVideogame(game);
        this.rentRepository.save(rent);

        RentResponse rentResponse = new RentResponse();
        rentResponse.set(rent);
        return ResponseEntity.ok(rentResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateAnUser(@PathVariable int id,@RequestBody User user){
        User userToUpdate = this.userRepository.findById(id).orElse(null);
        if (userToUpdate ==null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        //Check the fields
        if(user.getName().isEmpty() || user.getEmail().isEmpty()|| user.getPhone().isEmpty()){
            ErrorResponse error = new ErrorResponse();
            error.set("Fill in required fields");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPhone(user.getPhone());

        this.userRepository.save(userToUpdate);
        UserResponse userResponse = new UserResponse();
        userResponse.set(user);
        return ResponseEntity.ok(userResponse);
    }

    //Update rent for return
    @PutMapping("/rents/{id}")
    public ResponseEntity<Response<?>> updateARent(@PathVariable int id){
        Rent rent = this.rentRepository.findById(id).orElse(null);
        if (rent ==null){
            ErrorResponse error = new ErrorResponse();
            error.set("Rent not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        VideoGame game = rent.getVideogame();
        game.setBorrowed(false);

        //Set returned to right now
        LocalDateTime currentDateTime = LocalDateTime.now();
        rent.setReturnedAt(currentDateTime);

        this.rentRepository.save(rent);
        RentResponse rentResponse = new RentResponse();
        rentResponse.set(rent);
        return ResponseEntity.ok(rentResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteAUser(@PathVariable int id){
        User userToDelete = this.userRepository.findById(id).orElse(null);
        if (userToDelete ==null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        if(!userToDelete.getRents().isEmpty()){
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request, user is currently borrowing games");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        this.userRepository.delete(userToDelete);
        UserResponse userResponse = new UserResponse();
        userResponse.set(userToDelete);
        return ResponseEntity.ok(userResponse);}
}