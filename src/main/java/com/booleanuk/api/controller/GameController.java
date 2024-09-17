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
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("games")
public class GameController {
    @Autowired
    private GameRepository gameRepository;

    @GetMapping
    public ResponseEntity<GameListResponse> getAllGames() {
        GameListResponse gameListResponse = new GameListResponse();
        gameListResponse.set(this.gameRepository.findAll());
        return ResponseEntity.ok(gameListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getGameById(@PathVariable int id) {
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
    public ResponseEntity<Game> create(@RequestBody Game game){
        return new ResponseEntity<>(this.gameRepository.save(game), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Game> update(@PathVariable int id, @RequestBody Game game){
        Game gameToUpdate = this.gameRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No game with that id")
        );
        gameToUpdate.setTitle(game.getTitle());
        gameToUpdate.setTitle(game.getTitle());
        gameToUpdate.setStudio(game.getStudio());
        gameToUpdate.setRating(game.getRating());
        gameToUpdate.setPlayerCount(game.getPlayerCount());
        gameToUpdate.setGenre(game.getGenre());
        return new ResponseEntity<>(this.gameRepository.save(gameToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Game> delete(@PathVariable int id){
        Game gameToDelete = this.gameRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No game with that id")
        );
        this.gameRepository.delete(gameToDelete);
        return ResponseEntity.ok(gameToDelete);
    }

}
