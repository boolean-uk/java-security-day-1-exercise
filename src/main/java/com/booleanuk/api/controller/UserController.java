package com.booleanuk.api.controller;

import com.booleanuk.api.model.Game;
import com.booleanuk.api.model.Loan;
import com.booleanuk.api.model.User;

import com.booleanuk.api.repository.GameRepository;
import com.booleanuk.api.repository.LoanRepository;
import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.UserListResponse;
import com.booleanuk.api.response.UserResponse;
import com.booleanuk.api.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private LoanRepository loanRepository;



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
            error.set("User with that ID not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        UserResponse userResponse = new UserResponse();
        userResponse.set(user);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createUser(@RequestBody User user){
        if (isInvalidRequest(user)){
            return badRequest();
        }
        User createdUser = this.userRepository.save(user);
        createdUser.setCurrentlyBorrowedGames(new ArrayList<>());
        UserResponse userResponse = new UserResponse();
        userResponse.set(createdUser);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> UpdateUserById(@PathVariable int id, @RequestBody User user){

        if (isInvalidRequest(user)) {
            return badRequest();
        }

        User userToUpdate = this.getAUser(id);

        if(userToUpdate == null){
            return notFound();
        }

        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPhone(user.getPhone());
        this.userRepository.save(userToUpdate);

        UserResponse response = new UserResponse();
        response.set(userToUpdate);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteUserById(@PathVariable int id) {
        User userToDelete = this.getAUser(id);
        if (userToDelete == null){
            return this.notFound();
        }
        this.userRepository.delete(userToDelete);
        UserResponse response = new UserResponse();
        response.set(userToDelete);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/games/{gameId}/borrow")
    public ResponseEntity<Response<?>> borrowGame(@PathVariable int userId, @PathVariable int gameId) {
        User user = this.getAUser(userId);
        Game game = this.getAGame(gameId);
        if (user == null || game == null){
            return this.notFound();
        }

        if(game.isBorrowed()){
            ErrorResponse error = new ErrorResponse();
            error.set("Game is already borrowed");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }


        user.getCurrentlyBorrowedGames().add(game);
        game.setBorrowed(true);
        game.setBorrowedBy(user);


        Loan loan = new Loan(user, game);
        loan.setBorrowedAt(LocalDateTime.now());
        this.loanRepository.save(loan);

        this.userRepository.save(user);
        this.gameRepository.save(game);
        UserResponse response = new UserResponse();
        response.set(user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/games/{gameId}/return")
    public ResponseEntity<Response<?>> returnGame(@PathVariable int userId, @PathVariable int gameId) {
        User user = this.getAUser(userId);
        Game game = this.getAGame(gameId);

        if (user == null || game == null){
            return this.notFound();
        }

        if(game.getBorrowedBy() != user){
            ErrorResponse error = new ErrorResponse();
            error.set("Game is not borrowed by this user");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        user.getCurrentlyBorrowedGames().remove(game);
        //user.getBorrowHistory().add(game.getTitle());
        game.setBorrowed(false);
        game.setBorrowedBy(null);

        for(Loan loan : this.loanRepository.findAll()){
            if(loan.getUser() == user && loan.getGame() == game && loan.getReturnedAt() == null){
                loan.setReturnedAt(LocalDateTime.now());
            }
        }

        this.userRepository.save(user);
        this.gameRepository.save(game);
        UserResponse response = new UserResponse();
        response.set(user);
        return ResponseEntity.ok(response);
    }

    private User getAUser(int id){
        return this.userRepository.findById(id).orElse(null);
    }

    private Game getAGame(int id){
        return this.gameRepository.findById(id).orElse(null);
    }

    private boolean isInvalidRequest(User user){
        return user.getName() == null || user.getEmail() == null || user.getPhone() == null;
    }

    private ResponseEntity<Response<?>> badRequest(){
        ErrorResponse error = new ErrorResponse();
        error.set("Could not create User, please check all required fields are correct");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Response<?>> notFound(){
        ErrorResponse error = new ErrorResponse();
        error.set("No User with that id were found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
