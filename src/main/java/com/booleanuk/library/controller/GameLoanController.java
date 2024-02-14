package com.booleanuk.library.controller;

import com.booleanuk.library.model.Customer;
import com.booleanuk.library.model.Game;
import com.booleanuk.library.model.GameLoan;
import com.booleanuk.library.repository.CustomerRepository;
import com.booleanuk.library.repository.GameLoanRepository;
import com.booleanuk.library.repository.GameRepository;
import com.booleanuk.library.response.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/borrowgames")
public class GameLoanController {
    @Autowired
    private GameLoanRepository gameLoanRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private GameRepository gameRepository;

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomResponse> getGameLoansByCustomerId(@PathVariable int id) {
        if(!customerRepository.existsById(id)) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("not found")), HttpStatus.NOT_FOUND);
        }

        Customer customer = customerRepository
                .findById(id).get();

        CustomResponse customResponse = new CustomResponse("success", customer.getLoans());
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<CustomResponse> getGameLoansByGameId(@PathVariable int id) {
        if(!gameRepository.existsById(id)) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("not found")), HttpStatus.NOT_FOUND);
        }

        Game game = gameRepository
                .findById(id).get();

        CustomResponse customResponse = new CustomResponse("success", game.getLoans());
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @PostMapping("/customers/{id}/games/{gameId}")
    public ResponseEntity<CustomResponse> createGameLoan(@PathVariable int id, @PathVariable int gameId, @RequestBody GameLoan gameLoan) {
        if(!customerRepository.existsById(id) || !gameRepository.existsById(gameId)) {
            return new ResponseEntity<>(new CustomResponse("error", "not found"), HttpStatus.NOT_FOUND);
        }

        Customer customer = customerRepository
                .findById(id).get();
        gameLoan.setCustomer(customer);

        Game game = gameRepository
                .findById(gameId).get();
        gameLoan.setGame(game);

        gameLoanRepository.save(gameLoan);

        return new ResponseEntity<>(new CustomResponse("success", gameLoanRepository.findById(gameLoan.getId())), HttpStatus.OK);
    }
}