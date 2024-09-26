package com.booleanuk.api.controllers;

import com.booleanuk.api.models.BorrowGame;
import com.booleanuk.api.models.Customer;
import com.booleanuk.api.models.Game;
import com.booleanuk.api.repositories.BorrowGameRepository;
import com.booleanuk.api.repositories.GameRepository;
import com.booleanuk.api.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
public class BorrowGameController {
    private final BorrowGameRepository borrowGameRepository;
    private final GameRepository gameRepository;
    private final CustomerRepository customerRepository;

    @PostMapping("borrow/{customerId}/{gameId}")
    @ResponseStatus(HttpStatus.CREATED)
    public BorrowGame borrowGame(@PathVariable int gameId, @PathVariable int customerId) {
        if (gameRepository.findById(gameId).isEmpty() || customerRepository.findById(customerId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        BorrowGame borrowedItem = new BorrowGame();
        Game game = gameRepository.findById(gameId).get();
        if (borrowGameRepository.existsByGameAndIsActiveTrue(game)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "game is not available");
        }
        Customer customer = customerRepository.findById(customerId).get();

        borrowedItem.setGame(game);
        borrowedItem.setCustomer(customer);
        borrowedItem.setActive(true);

        return borrowGameRepository.save(borrowedItem);
    }

    @PostMapping("return/{customerId}/{gameId}")
    public BorrowGame returnGame(@PathVariable int gameId, @PathVariable int customerId) {
        if (gameRepository.findById(gameId).isEmpty() || customerRepository.findById(customerId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Game game = gameRepository.findById(gameId).get();
        Customer customer = customerRepository.findById(customerId).get();
        if (borrowGameRepository.findByCustomerAndGame(customer, game).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        BorrowGame borrowedGame = borrowGameRepository.findByCustomerAndGame(customer, game).get();
        borrowedGame.setEndDate(LocalDateTime.now());
        borrowedGame.setActive(false);

        return borrowGameRepository.save(borrowedGame);
    }
}
