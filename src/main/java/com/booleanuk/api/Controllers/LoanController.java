package com.booleanuk.api.Controllers;

import com.booleanuk.api.Models.Loan;
import com.booleanuk.api.Models.User;
import com.booleanuk.api.Models.VideoGame;
import com.booleanuk.api.Repositories.LoanRepository;
import com.booleanuk.api.Repositories.UserRepository;
import com.booleanuk.api.Repositories.VideoGameRepository;
import com.booleanuk.api.Responses.ErrorResponse;
import com.booleanuk.api.Responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("loan")
public class LoanController {
    @Autowired
    private LoanRepository repo;

    @Autowired
    private UserRepository users;

    @Autowired
    private VideoGameRepository videoGames;

    @PostMapping
    public ResponseEntity<Response<?>> newLoan(@RequestBody Loan loan, @RequestParam int videoGameId, @RequestParam int userId){
        VideoGame videoGame = videoGames.findById(videoGameId).orElse(null);
        User user = users.findById(userId).orElse(null);

        if (videoGame == null || user == null){
            ErrorResponse notFound = new ErrorResponse();
            notFound.set("not found");

            return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
        }

        loan.setVideoGame(videoGame);
        loan.setUser(user);
        repo.save(loan);

        Response<Loan> loanResponse = new Response<>();
        loanResponse.set(loan);

        return new ResponseEntity<>(loanResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> endLoan(@PathVariable int id){
        Loan toDelete = repo.findById(id).orElse(null);

        if (toDelete == null){
            ErrorResponse notFound = new ErrorResponse();
            notFound.set("not found");

            return new ResponseEntity<>(notFound, HttpStatus.CREATED);
        }

        repo.delete(toDelete);
        Response<Loan> loanResponse = new Response<>();
        loanResponse.set(toDelete);

        return ResponseEntity.ok(loanResponse);
    }
}
