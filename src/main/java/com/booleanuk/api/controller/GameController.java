package com.booleanuk.api.controller;

import com.booleanuk.api.model.Game;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.GameListResponse;
import com.booleanuk.api.response.GameResponse;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.respository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("games")
public class GameController {

    @Autowired
    private GameRepository games;

    @PostMapping
    public ResponseEntity<GameResponse> create(@RequestBody Game toAdd) {
        GameResponse gameResponse = new GameResponse();
        gameResponse.set(this.games.save(toAdd));
        return new ResponseEntity<>(gameResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<GameListResponse> getAll() {
        GameListResponse gameListResponse = new GameListResponse();
        gameListResponse.set(this.games.findAll());
        return new ResponseEntity<>(gameListResponse, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getOne(@PathVariable(name = "id") int id) {
        Game toReturn = this.games.findById(id)
                .orElse(
                        null
                );

        // 404 not found
        if (toReturn == null) {
            ErrorResponse error = new ErrorResponse("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        GameResponse gameResponse = new GameResponse();
        gameResponse.set(toReturn);

        return new ResponseEntity<>(gameResponse, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> update(@PathVariable(name = "id") int id, @RequestBody Game newData) {
        Game toUpdate = this.games.findById(id)
                .orElse(
                        null
                );

        // 404 not found
        if (toUpdate == null) {
            ErrorResponse error = new ErrorResponse("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        toUpdate.setTitle(newData.getTitle());
        toUpdate.setGenre(newData.getGenre());
        toUpdate.setGameStudio(newData.getGameStudio());
        toUpdate.setAgeRating(newData.getAgeRating());
        toUpdate.setNumberOfPlayers(newData.getNumberOfPlayers());

        GameResponse gameResponse = new GameResponse();
        gameResponse.set(this.games.save(toUpdate));

        return new ResponseEntity<>(gameResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable(name = "id") int id) {
        Game toDelete = this.games.findById(id)
                .orElse(
                        null
                );

        // 404 not found
        if (toDelete == null) {
            ErrorResponse error = new ErrorResponse("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        this.games.delete(toDelete);

        GameResponse gameResponse = new GameResponse();
        gameResponse.set(toDelete);

        return new ResponseEntity<>(gameResponse, HttpStatus.OK);
    }

}
