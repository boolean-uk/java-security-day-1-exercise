package com.booleanuk.api.controller;

import com.booleanuk.api.model.DTO.LendDTO;
import com.booleanuk.api.model.Game;
import com.booleanuk.api.model.Lend;
import com.booleanuk.api.model.User;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.LendListResponse;
import com.booleanuk.api.response.LendResponse;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.respository.GameRepository;
import com.booleanuk.api.respository.LendRepository;
import com.booleanuk.api.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("lend")
public class LendController {

    @Autowired
    private LendRepository lends;

    @Autowired
    private UserRepository users;

    @Autowired
    private GameRepository games;

    @PostMapping
    public ResponseEntity<Response<?>> newLend(@RequestBody LendDTO request) {
        User user = this.users.findById(request.getUserId().getId()).orElse(null);

        Game game = this.games.findById(request.getGameId().getId()).orElse(null);

        if (user == null || game == null) {
            ErrorResponse errorResponse = new ErrorResponse("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        if(game.isOccupied()){
            ErrorResponse errorResponse = new ErrorResponse("game is occupied");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        game.setOccupied(true);
        Lend lend = new Lend(request.getUserId(), request.getGameId(), true);

        this.lends.save(lend);
        user.addLend(lend);
        game.addLend(lend);

        LendResponse lendResponse = new LendResponse();
        lendResponse.set(lend);

        return new ResponseEntity<>(lendResponse, HttpStatus.CREATED);
    }

    @PostMapping("/return")
    public ResponseEntity<Response<?>> returnLend(@RequestBody LendDTO request) {
        User user = this.users.findById(request.getUserId().getId()).orElse(null);

        Game game = this.games.findById(request.getGameId().getId()).orElse(null);

        if (user == null || game == null) {
            ErrorResponse errorResponse = new ErrorResponse("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        game.setOccupied(false);
        this.games.save(game);

        Lend lend = this.lends.findByUserIdAndGameId(user.getId(), game.getId());

        if (lend == null) {
            ErrorResponse errorResponse = new ErrorResponse("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        lend.setActive(false);

        LendResponse lendResponse = new LendResponse();
        lendResponse.set(this.lends.save(lend));
        return new ResponseEntity<>(lendResponse, HttpStatus.OK);
    }

}
