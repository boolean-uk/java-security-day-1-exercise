package com.booleanuk.api.library.controller;


import com.booleanuk.api.library.model.Game;
import com.booleanuk.api.library.model.Loan;
import com.booleanuk.api.library.model.User;
import com.booleanuk.api.library.repository.GameRepository;
import com.booleanuk.api.library.repository.LoanRepository;
import com.booleanuk.api.library.repository.UserRepository;
import com.booleanuk.api.library.response.ErrorResponse;
import com.booleanuk.api.library.response.LoanListResponse;
import com.booleanuk.api.library.response.LoanResponse;
import com.booleanuk.api.library.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @PostMapping("/{userId}/loans/{gameId}")
    public ResponseEntity<Response<?>> createLoan(@PathVariable int userId, @PathVariable int gameId, @RequestBody Loan loan) {

        User user = this.getAUser(userId);
        Game game = this.getAGame(gameId);
        if(user == null || game == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        loan.setGame(game);
        loan.setUser(user);
        Loan savedLoan = this.loanRepository.save(loan);
        LoanResponse loanResponse = new LoanResponse();
        loanResponse.set(savedLoan);
        return new ResponseEntity<>(loanResponse, HttpStatus.CREATED);

    }

    @GetMapping("/{userId}/loans")
    public ResponseEntity<Response<?>> getLoansUser(@PathVariable int userId) {
        User user = getAUser(userId);

        if(user == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No User with that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        LoanListResponse loanListResponse = new LoanListResponse();
        loanListResponse.set(user.getLoans());
        return ResponseEntity.ok(loanListResponse);
    }

    private User getAUser(int id){
        return this.userRepository.findById(id).orElse(null);
    }
    private Game getAGame(int id){
        return this.gameRepository.findById(id).orElse(null);
    }
}





