package com.booleanuk.api.controller;

import com.booleanuk.api.model.Game;
import com.booleanuk.api.repository.GameRepository;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.GameListResponse;
import com.booleanuk.api.response.GameResponse;
import com.booleanuk.api.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("games")
public class GameController {
    @Autowired
    private GameRepository gameRepository;

    @GetMapping
    public ResponseEntity<GameListResponse> getAllGames() {
        GameListResponse bookListResponse = new GameListResponse();
        bookListResponse.set(this.gameRepository.findAll());
        return ResponseEntity.ok(bookListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getGamesById(@PathVariable int id) {
        Game game = this.gameRepository.findById(id).orElse(null);
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
    public ResponseEntity<Response<?>> createGame(@RequestBody Game game) {
        //Check the fields
        if(game.getTitle().isEmpty() || game.getAgeRating().isEmpty()|| game.getGameStudio().isEmpty() || game.getGenre().isEmpty() || game.getNumPlayers()<=0){
            ErrorResponse error = new ErrorResponse();
            error.set("Fill in required fields");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        this.gameRepository.save(game);

        GameResponse gameResponse = new GameResponse();
        gameResponse.set(game);
        return ResponseEntity.ok(gameResponse);
    }
}
