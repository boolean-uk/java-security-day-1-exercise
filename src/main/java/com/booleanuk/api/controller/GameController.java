package com.booleanuk.api.controller;

import com.booleanuk.api.model.Game;
import com.booleanuk.api.repository.GameRepository;
import com.booleanuk.api.response.GameListResponse;
import com.booleanuk.api.response.GameResponse;
import com.booleanuk.api.response.ErrorResponse;
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
    private GameRepository repository;

    @GetMapping
    public ResponseEntity<GameListResponse> getAllBooks() {
        GameListResponse gameListResponse = new GameListResponse();
        gameListResponse.set(this.repository.findAll());
        return ResponseEntity.ok(gameListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getBookById(@PathVariable int id) {
        Game game = this.repository.findById(id).orElse(null);
        if (game == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        GameResponse gameResponse = new GameResponse();
        gameResponse.set(game);
        return ResponseEntity.ok(gameResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Game game) {

        if (game.getTitle() == null ||
                game.getGamestudio() == null ||
                game.getAgerating() == null ||
                game.getNumberOfPlayers() == null ||
                game.getGenre() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        this.repository.save(game);

        GameResponse gameResponse = new GameResponse();
        gameResponse.set(game);
        return new ResponseEntity<>(gameResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody Game request) {
        if (request.getTitle() == null ||
                request.getGamestudio() == null ||
                request.getAgerating() == null ||
                request.getNumberOfPlayers() == null ||
                request.getGenre() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Game game = this.repository.findById(id).orElse(null);
        if (game == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        game.setTitle(request.getTitle());
        game.setGamestudio(request.getGamestudio());
        game.setAgerating(request.getAgerating());
        game.setNumberOfPlayers(request.getNumberOfPlayers());
        game.setGenre(request.getGenre());
        game = this.repository.save(game);

        GameResponse gameResponse = new GameResponse();
        gameResponse.set(game);
        return new ResponseEntity<>(gameResponse, HttpStatus.CREATED);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id) {
        Game game = this.repository.findById(id).orElse(null);
        if (game == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.repository.delete(game);
        GameResponse gameResponse = new GameResponse();
        gameResponse.set(game);
        return ResponseEntity.ok(gameResponse);
    }
}
