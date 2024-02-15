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

import java.util.List;

@RestController
@RequestMapping("loans")
public class LoanController {
    @Autowired
    private LoanRepository repo;

    @Autowired
    private UserRepository users;

    @Autowired
    private VideoGameRepository videoGames;

    @PostMapping
    public ResponseEntity<Response<?>> newLoan(@RequestBody Loan loan, @RequestParam int video_game_id, @RequestParam int user_id){
        VideoGame videoGame = videoGames.findById(video_game_id).orElse(null);
        User user = users.findById(user_id).orElse(null);

        if (videoGame == null || user == null){
            ErrorResponse notFound = new ErrorResponse();
            notFound.set("not found");

            return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
        }

        if (loan.getLoanedTo() == null || loan.getLoanedFrom() == null){
            ErrorResponse badRequest = new ErrorResponse();
            badRequest.set("bad request");

            return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
        }

        loan.setVideoGame(videoGame);
        loan.setUser(user);


        Response<Loan> loanResponse = new Response<>();
        loanResponse.set(repo.save(loan));

        return new ResponseEntity<>(loanResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<List<Loan>>> getAll(){
        Response<List<Loan>> loanResponse = new Response<>();
        loanResponse.set(repo.findAll());

        return ResponseEntity.ok(loanResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getOne(@PathVariable int id){
        Loan loan = repo.findById(id).orElse(null);

        if (loan == null){
            ErrorResponse notFound = new ErrorResponse();
            notFound.set("not found");

            return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
        }

        Response<Loan> loanResponse = new Response<>();
        loanResponse.set(loan);

        return ResponseEntity.ok(loanResponse);
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
