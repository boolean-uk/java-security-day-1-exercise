package com.booleanuk.api.library.controller;

import com.booleanuk.api.library.model.Game;
import com.booleanuk.api.library.model.User;
import com.booleanuk.api.library.model.UserRecord;
import com.booleanuk.api.library.repository.GameRepository;
import com.booleanuk.api.library.repository.UserRecordRepository;
import com.booleanuk.api.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserRecordRepository userRecordRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getOneUser(@PathVariable int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @PostMapping("/{userId}/borrow/{gameId}")
    public ResponseEntity<User> borrowGame(@PathVariable int userId, @PathVariable int gameId){
        Game game = this.gameRepository.findById(gameId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if(game.isOnLoan()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game already out on loan");
        }
        game.setOnLoan(true);
        game.setUser(user);
        user.getGames().add(game);
        this.userRecordRepository.save(new UserRecord(user, game));
        this.userRepository.save(user);
        this.gameRepository.save(game);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping("/{userId}/return/{gameId}")
    public ResponseEntity<User> returnGame(@PathVariable int userId, @PathVariable int gameId){
        Game game = this.gameRepository.findById(gameId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        game.setUser(null);
        game.setOnLoan(false);
        user.getGames().remove(game);
        this.gameRepository.save(game);
        this.userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        userToUpdate.setName(user.getName());



        return new ResponseEntity<>(userRepository.save(userToUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        try{
            userRepository.delete(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
}
