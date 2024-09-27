package com.booleanuk.api.controller;

import com.booleanuk.api.model.Game;
import com.booleanuk.api.model.User;
import com.booleanuk.api.model.Loan;
import com.booleanuk.api.repository.GameRepository;
import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.api.repository.LoanRepository;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;

    @GetMapping("/users/{userId}/games/{gameId}")
    public ResponseEntity<Response<?>> getAllLoans(@PathVariable int userId, @PathVariable int gameId) {
        User tempUser = userRepository.findById(userId).orElse(null);
        Game tempGame = gameRepository.findById(gameId).orElse(null);

        if(tempUser == null || tempGame == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }

        //Find all loans that have the given game and user id
        List<Loan> loans = loanRepository.findAll().stream()
                .filter(loan ->
                        loan.getGame().getId() == gameId && loan.getUser().getId() == userId)
                .toList();


        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(loans));
    }

    @GetMapping("/users/{userId}/loans")
    public ResponseEntity<Response<?>> getAllUserLoans(@PathVariable int userId) {
        User user = userRepository.findById(userId).orElse(null);

        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(user.getLoans()));
    }

    @GetMapping("/games/{gameId}/loans")
    public ResponseEntity<Response<?>> getAllGameLoans(@PathVariable int gameId) {
        Game game = gameRepository.findById(gameId).orElse(null);

        if(game == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(game.getLoans()));
    }

    @PostMapping("/users/{userId}/games/{gameId}")
    public ResponseEntity<Response<?>> createLoan(@PathVariable int gameId, @PathVariable int userId, @RequestBody Loan loan) {
        User tempUser = userRepository.findById(userId).orElse(null);
        Game tempGame = gameRepository.findById(gameId).orElse(null);

        if(tempUser == null || tempGame == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }

        loan.setGame(tempGame);
        loan.setUser(tempUser);

        if(loan.getActive() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("bad request"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(loanRepository.save(loan)));
    }

    @PutMapping("/loans/{id}")
    public ResponseEntity<Response<?>> updateLoan(@PathVariable int id, @RequestBody Loan loan) {
        Loan loanToUpdate = loanRepository.findById(id).orElse(null);

        if(loanToUpdate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }

        if(loan.getActive() == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("bad request"));
        }

        loanToUpdate.setActive(loan.getActive());
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(loanRepository.save(loanToUpdate)));

    }

}
