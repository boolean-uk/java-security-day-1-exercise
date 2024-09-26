package com.booleanuk.api.controller;


import com.booleanuk.api.exceptions.CustomDataNotFoundException;
import com.booleanuk.api.exceptions.CustomParamaterConstraintException;
import com.booleanuk.api.model.Game;
import com.booleanuk.api.model.Library;
import com.booleanuk.api.model.Loan;
import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.GameRepository;
import com.booleanuk.api.repository.LibraryRepository;
import com.booleanuk.api.repository.LoanRepository;
import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.response.SuccessResponse;
import com.booleanuk.api.util.DateCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("loans")
public class LoanController {


    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loans = this.loanRepository.findAll();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }


    @GetMapping("game/{id}/history")
    public ResponseEntity<Response<?>> getGameLoans(@PathVariable (name = "id") int id) {
        Game game = this.getAGame(id);
        if(game.getLoansHistory() == null) {
            game.setLoansHistory(new ArrayList<>());
        }

        this.checkValidInput(game);

        return new ResponseEntity<>(new SuccessResponse(game.getLoansHistory()), HttpStatus.OK);


    }


    @GetMapping("user/{id}/history")
    public ResponseEntity<Response<?>> getUserLoans(@PathVariable (name = "id") int id) {
        User user = this.getAUser(id);
        if(user.getLoansHistory() == null) {
            user.setLoansHistory(new ArrayList<>());
        }

        this.checkValidInput(user);

        return new ResponseEntity<>(new SuccessResponse(user.getLoansHistory()), HttpStatus.OK);
    }


    @PostMapping("user/{id}/game/{gameId}")
    public ResponseEntity<Response<?>> createLoan(@PathVariable (name = "id") int id,
                                                  @PathVariable (name = "gameId") int gameId,
                                                  @RequestBody Loan loan) {

        User user = this.getAUser(id);
        Game game = this.getAGame(gameId);

        this.checkValidInput(user);
        this.checkValidInput(game);


        Loan newLoan = new Loan(loan.getTitle(), DateCreater.getCurrentDate(), DateCreater.getCurrentDate(), user, game);

        this.checkValidInput(newLoan);

        game.getLoans().add(newLoan);
        game.getLoansHistory().add(newLoan);
        user.getLoansHistory().add(newLoan);
        user.getLoans().add(newLoan);

        this.loanRepository.save(newLoan);


        return new ResponseEntity<>(new SuccessResponse(newLoan), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteLoan(@PathVariable (name = "id") int id) {
        Loan loan = this.getALoan(id);

        loan.getGame().getLoans().remove(loan);
        loan.getUser().getLoans().remove(loan);

        this.loanRepository.delete(loan);


        return new ResponseEntity<>(new SuccessResponse(loan), HttpStatus.OK);
    }









    private Loan getALoan(int id) {
        return this.loanRepository.findById(id).orElseThrow(() -> new CustomDataNotFoundException("No loan with that ID found"));
    }
    private User getAUser(int id) {
        return this.userRepository.findById(id).orElseThrow(() -> new CustomDataNotFoundException("No user with that ID found"));
    }


    private Game getAGame(int id) {
        return this.gameRepository.findById(id).orElseThrow(() -> new CustomDataNotFoundException("No game with that ID found"));
    }

    private void checkValidInput(User user) {
        if(user.getCreatedAt() == null || user.getName() == null || user.getUpdatedAt() == null) {
            throw new CustomParamaterConstraintException("Bad request");
        }
    }
    private void checkValidInput(Game game) {
        if(game.getGameStudio() == null || game.getGenre() == null || game.getTitle() == null) {
            throw new CustomParamaterConstraintException("Bad request");
        }
    }

    private void checkValidInput(Loan loan) {
        if(loan.getCreatedAt() == null || loan.getUpdatedAt() == null) {
            throw new CustomParamaterConstraintException("Bad request");
        }
    }
}
