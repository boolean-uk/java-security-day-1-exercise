package com.booleanuk.api.controller;

import com.booleanuk.api.model.User;
import com.booleanuk.api.model.VideoGame;
import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.api.repository.VideoGameRepository;
import com.booleanuk.api.response.*;
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
    private UserRepository userRepository;

    @Autowired
    private VideoGameRepository videoGameRepository;

    @GetMapping
    public ResponseEntity<UserListResponse> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        UserListResponse userListResponse = new UserListResponse();
        userListResponse.set(users);
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

    //Extension - Borrow game
    @PostMapping("/{userId}/borrow/{gameId}")
    public ResponseEntity<Response<?>> borrowGame(@PathVariable int userId, @PathVariable int gameId) {
        User user = userRepository.findById(userId).orElse(null);
        VideoGame videoGame = videoGameRepository.findById(gameId).orElse(null);
        if (user == null || videoGame == null || videoGame.isBorrowed()) {
            ErrorResponse error = new ErrorResponse();
            error.set("Game cannot be borrowed");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        videoGame.setBorrowed(true);
        videoGame.setCurrentBorrower(user);

        //Save specific user in list that have borrowed this specific game
        //Saves specific game in a list borrowed by a specific user
        videoGame.getUserGameBorrowers().add(user);
        user.getBorrowedGames().add(videoGame);

        this.videoGameRepository.save(videoGame);

        UserResponse userResponse = new UserResponse();
        userResponse.set(user);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    //Extension - Return game
    @PostMapping("/{userId}/return/{gameId}")
    public ResponseEntity<Response<?>> returnGame(@PathVariable int userId, @PathVariable int gameId) {
        VideoGame videoGame = videoGameRepository.findById(gameId).orElse(null);
        if (videoGame == null || !videoGame.isBorrowed() || videoGame.getUser().getId() != userId) {
            ErrorResponse error = new ErrorResponse();
            error.set("Game cannot be returned");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        videoGame.setBorrowed(false);
        videoGame.setCurrentBorrower(null);
        this.videoGameRepository.save(videoGame);

        VideoGameResponse videoGameResponse = new VideoGameResponse();
        videoGameResponse.set(videoGame);
        return ResponseEntity.ok(videoGameResponse);
    }

    //Extension - Who is currently borrowing a game
    @GetMapping("/borrowing/{id}")
    public ResponseEntity<Response<?>> getUserBorrowingGame(@PathVariable int id) {
        VideoGame videoGame = this.videoGameRepository.findById(id).orElse(null);

        if(videoGame == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No video game matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        User user = videoGame.getCurrentBorrower();

        UserResponse userResponse = new UserResponse();
        userResponse.set(user);
        return ResponseEntity.ok(userResponse);
    }

    //Extension - Get users who have borrowed a game
    @GetMapping("/borrowed/{id}")
    public ResponseEntity<Response<?>> getUsersWhoHaveBorrowedGame(@PathVariable int id) {
        VideoGame videoGame = this.videoGameRepository.findById(id).orElse(null);

        if(videoGame == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No video game matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        List<User> users = videoGame.getUserGameBorrowers();

        UserListResponse userListResponse = new UserListResponse();
        userListResponse.set(users);
        return new ResponseEntity<>(userListResponse, HttpStatus.CREATED);
    }

    //Extension - Get games borrowed by a user
    @GetMapping("/{id}/borrowed")
    public ResponseEntity<Response<?>> getGamesBorrowedByAUser(@PathVariable int id) {
        User user = userRepository.findById(id).orElse(null);

        if(user == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No user matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        List<VideoGame> videoGamesBorrowed = user.getBorrowedGames();

        VideoGameListResponse videoGameListResponse = new VideoGameListResponse();
        videoGameListResponse.set(videoGamesBorrowed);
        return new ResponseEntity<>(videoGameListResponse, HttpStatus.CREATED);
    }
}
