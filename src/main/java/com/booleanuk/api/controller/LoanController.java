package com.booleanuk.api.controller;

import com.booleanuk.api.model.Game;
import com.booleanuk.api.model.Loan;
import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.GameRepository;
import com.booleanuk.api.repository.LoanRepository;
import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.api.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("loans")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;


    @GetMapping("/users/{userId}")
    public ResponseEntity<Response<?>> getLoansForUser(@PathVariable int userId) {
        List<Loan> loans = new ArrayList<>();
        User user = getAUser(userId);

        if(user == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No User with that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        for(Loan loan : this.loanRepository.findAll()){
            if (loan.getUser() == user){
                loans.add(loan);
            }
        }

        LoanListResponse loanListResponse = new LoanListResponse();
        loanListResponse.set(loans);
        return ResponseEntity.ok(loanListResponse);
    }

    @GetMapping("/games/{gameId}")
    public ResponseEntity<Response<?>> getLoansForGame(@PathVariable int gameId) {
        List<Loan> loans = new ArrayList<>();
        Game game = getAGame(gameId);

        if(game == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No Game with that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        for(Loan loan : this.loanRepository.findAll()){
            if (loan.getGame() == game){
                loans.add(loan);
            }
        }

        LoanListResponse loanListResponse = new LoanListResponse();
        loanListResponse.set(loans);
        return ResponseEntity.ok(loanListResponse);
    }


    private User getAUser(int id){
        return this.userRepository.findById(id).orElse(null);
    }

    private Game getAGame(int id){
        return this.gameRepository.findById(id).orElse(null);
    }

}
