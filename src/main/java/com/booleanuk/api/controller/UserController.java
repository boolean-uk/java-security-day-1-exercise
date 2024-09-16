package com.booleanuk.api.controller;

import com.booleanuk.api.models.Game;
import com.booleanuk.api.models.User;
import com.booleanuk.api.repositories.GameRepository;
import com.booleanuk.api.repositories.UserRepository;
import com.booleanuk.api.responses.ErrorResponse;
import com.booleanuk.api.responses.Response;
import com.booleanuk.api.responses.UserListResponse;
import com.booleanuk.api.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAllUsers(){
        List<User> users = this.userRepository.findAll();

        UserListResponse userListResponse = new UserListResponse();
        userListResponse.set(users);

        return ResponseEntity.ok(userListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getAUser(@PathVariable int id){
        User user = this.userRepository.findById(id).orElse(null);

        if (user == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No user with tha id is found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        UserResponse userResponse = new UserResponse();
        userResponse.set(user);

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createUser(@RequestBody User user){
        if (user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null || user.getPhone() == null){
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create a new user, please check all fields are correct");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        this.userRepository.save(user);

        UserResponse userResponse = new UserResponse();
        userResponse.set(user);

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}/games/{gamesId}")
    public ResponseEntity<Response<?>> updateUser(@PathVariable int userId, @PathVariable(required = false) int gamesId, @RequestBody User user){
        User userToUpdate = this.userRepository.findById(userId).orElse(null);
        Game game = this.gameRepository.findById(gamesId).orElse(null);

        if (userToUpdate == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No user with that id to update");

            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPhone(user.getPhone());

        if (game != null){
            game.setUser(userToUpdate);
            userToUpdate.getGames().add(game);
        }

        this.userRepository.save(userToUpdate);

        UserResponse userResponse = new UserResponse();
        userResponse.set(userToUpdate);

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteUser(@PathVariable int id){
        User userToRemove = this.userRepository.findById(id).orElse(null);

        if (userToRemove == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No user with that id found");

            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        this.userRepository.delete(userToRemove);

        UserResponse userResponse = new UserResponse();
        userResponse.set(userToRemove);

        return ResponseEntity.ok(userResponse);
    }


}
