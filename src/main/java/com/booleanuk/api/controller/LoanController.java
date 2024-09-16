package com.booleanuk.api.controller;

import com.booleanuk.api.model.Game;
import com.booleanuk.api.model.Loan;
import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.GameRepository;
import com.booleanuk.api.repository.LoanRepository;

import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.LoanListResponse;
import com.booleanuk.api.response.LoanResponse;
import com.booleanuk.api.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games/{gameId}/users/{userId}")
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getLoansByGameAndUser(@PathVariable("gameId") int gameId, @PathVariable("userId") int userId) {

        User user = this.userRepository.findById(userId).orElse(null);
        if (user == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("User not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        Game game = this.gameRepository.findById(gameId).orElse(null);
        if (game == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Game not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        LoanListResponse loanListResponse = new LoanListResponse();
        loanListResponse.set(this.loanRepository.findByUserAndGame(user, game));
        return ResponseEntity.ok(loanListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createLoan(@PathVariable("gameId") int gameId, @PathVariable("userId") int userId, @RequestBody Loan loan) {
        System.out.println(userId);
        System.out.println(this.userRepository.findAll());
        User user = this.userRepository.findById(userId).orElse(null);
        if (user == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("User not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        Game game = this.gameRepository.findById(gameId).orElse(null);
        if (game == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Game not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        if (!this.loanRepository.findByReturnedAndGame(false, game).isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Game already loaned out");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        LoanResponse loanResponse = new LoanResponse();
        loan.setGame(game);
        loan.setUser(user);
        this.loanRepository.save(loan);
        loanResponse.set(loan);
        return new ResponseEntity<>(loanResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> returnGame(@PathVariable("gameId") int gameId, @PathVariable("userId") int userId, @PathVariable("id") int id) {
        Loan loan = this.loanRepository.findById(id).orElse(null);
        if (loan == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Loan not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        User user = this.userRepository.findById(userId).orElse(null);
        if (user == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("User not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        Game game = this.gameRepository.findById(gameId).orElse(null);
        if (game == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Game not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        loan.setReturned(true);
        this.loanRepository.save(loan);
        LoanResponse loanResponse = new LoanResponse();
        loanResponse.set(loan);
        return new ResponseEntity<>(loanResponse, HttpStatus.CREATED);
    }
}
