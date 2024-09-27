package com.booleanuk.api.loan;

import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.LoanListResponse;
import com.booleanuk.api.response.LoanResponse;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.user.User;
import com.booleanuk.api.user.UserRepository;
import com.booleanuk.api.videogame.Game;
import com.booleanuk.api.videogame.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("users")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @GetMapping("{userId}/loans")
    public ResponseEntity<Response> getLoan(@PathVariable int userId) {
        User user = this.userRepository
                .findById(userId)
                .orElse(null);
        if (user == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("User not found")), HttpStatus.NOT_FOUND);
        }

        List<Loan> loans = loanRepository.findByUser(user);
        if (loans.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No loans found");
        }
        return new ResponseEntity<>(new LoanListResponse(loans), HttpStatus.OK);
    }

    @PostMapping("{userId}/loans/{gameId}")
    public ResponseEntity<Response> createLoan(@PathVariable int userId, @PathVariable int gameId, @RequestBody Loan loan) {
        User user = this.userRepository
                .findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No User found"));

        Game game = this.gameRepository
                .findById(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Game found"));

        loan.setUser(user);
        loan.setGame(game);

        this.loanRepository.save(loan);
        return new ResponseEntity<>(new LoanResponse(loan), HttpStatus.CREATED);
    }

}
