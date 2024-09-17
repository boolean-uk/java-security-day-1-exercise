package com.booleanuk.api.controller;

import com.booleanuk.api.model.Game;
import com.booleanuk.api.model.Loan;
import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.GameRepository;
import com.booleanuk.api.repository.LoanRepository;
import com.booleanuk.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("users/{userId}/games/{gameId}")
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;

    @PostMapping
    public ResponseEntity<Loan> create(@RequestBody Loan loan, @PathVariable int userId, @PathVariable int gameId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with that id")
        );
        Game game = this.gameRepository.findById(gameId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No game with that id")
        );
        loan.setUser(user);
        loan.setGame(game);
        return new ResponseEntity<>(this.loanRepository.save(loan), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Loan> getAllByUserAndGameId(@PathVariable int userId, @PathVariable int gameId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with that id")
        );
        Game game = this.gameRepository.findById(gameId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No game with that id")
        );
        return this.loanRepository.findAll();
    }
}
