package com.booleanuk.api.controller;

import com.booleanuk.api.exception.ApiException;
import com.booleanuk.api.model.Game;
import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.GameRepository;
import com.booleanuk.api.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("games")
public class GameController {
    @Autowired
    GameRepository repository;

    @GetMapping
    public ResponseEntity<Response<List<Game>>> getAll() {
        return ResponseEntity.ok(new Response<>(this.repository.findAll()));
    }

    @PostMapping
    public ResponseEntity<Response<Game>> create(@RequestBody Game game) {
        if (game.getGameStudio() == null || game.getTitle() == null || game.getGenre() == null || game.getAgeRating() <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "bad request");
        }
        Game createdGame = this.repository.save(game);
        createdGame.setBorrowedGames(new ArrayList<>());
        return new ResponseEntity<>(new Response<>(createdGame), HttpStatus.CREATED);
    }
}
