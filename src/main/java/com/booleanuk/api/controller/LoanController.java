package com.booleanuk.api.controller;

import com.booleanuk.api.dto.LoanGameDTO;
import com.booleanuk.api.dto.LoanUserDTO;
import com.booleanuk.api.model.Game;
import com.booleanuk.api.model.Loan;
import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.GameRepository;
import com.booleanuk.api.repository.LoanRepository;
import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.api.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/loans")
public class LoanController {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/users/{userId}/game/{gameId}")
    public ResponseEntity<?> createLoan(@PathVariable int userId, @PathVariable int gameId) {
        Response<Object> response = new Response<>();
        User user = userRepository.findById(userId).orElse(null);
        Game game = gameRepository.findById(gameId).orElse(null);

        if (user == null || game == null) {
            response.setError("not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            Loan loan = new Loan(user, game);
            Loan newLoan = loanRepository.save(loan);
            response.setSuccess(newLoan);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setError("bad request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> returnLoan(@PathVariable int id) {
        Response<Object> response = new Response<>();
        Loan loan = loanRepository.findById(id).orElse(null);
        if (loan == null) {
            response.setError("not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            loan.setReturned(true);
            Loan returnedLoan = loanRepository.save(loan);
            response.setSuccess(returnedLoan);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setError("bad request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getLoansByUser(@PathVariable int userId) {
        Response<Object> response = new Response<>();
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            response.setError("not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        List<Loan> loans = loanRepository.findGamesByUserId(userId);
        List<LoanGameDTO> loanGameDTOs = loans.stream()
                .map(loan -> new LoanGameDTO(loan.getId(), loan.isReturned(), loan.getGame().getTitle()))
                .collect(Collectors.toList());
        response.setSuccess(loanGameDTOs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/games/{gameId}")
    public ResponseEntity<?> getLoansByGame(@PathVariable int gameId) {
        Response<Object> response = new Response<>();
        Game game = gameRepository.findById(gameId).orElse(null);
        if (game == null) {
            response.setError("not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        List<Loan> loans = loanRepository.findUsersByGameId(gameId);
        List<LoanUserDTO> loanUserDTOs = loans.stream()
                .map(loan -> new LoanUserDTO(loan.getId(), loan.isReturned(), loan.getUser().getName()))
                .collect(Collectors.toList());
        response.setSuccess(loanUserDTOs);
        return ResponseEntity.ok(response);
    }
}
