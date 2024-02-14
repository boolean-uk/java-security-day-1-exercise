package com.booleanuk.api.controller;

import com.booleanuk.api.model.Game;
import com.booleanuk.api.repository.GameRepository;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("games")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @GetMapping
    public ResponseEntity<Response<List<Game>>> getAllGames() {
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(this.gameRepository.findAll()));
    }

    @PostMapping
    public ResponseEntity<Response<?>> createGame(@RequestBody Game game) {
        if(containsNull(game)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("bad request"));
        }
        Response<Game> response = new SuccessResponse<>(gameRepository.save(game));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //TODO: ensure game can't be deleted if loans exists
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteGame(@PathVariable int id) {
        Game gameToDelete = findGame(id);
        if(gameToDelete == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }

        gameRepository.delete(gameToDelete);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(gameToDelete));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateGame(@PathVariable int id, @RequestBody Game game) {
        Game gameToUpdate = findGame(id);
        if(gameToUpdate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }

        if(game.getTitle() != null) {
            gameToUpdate.setTitle(game.getTitle());
        }
        if(game.getGameStudio() != null) {
            gameToUpdate.setGameStudio(game.getGameStudio());
        }
        if(game.getAgeRating() != null) {
            gameToUpdate.setAgeRating(game.getAgeRating());
        }
        if(game.getNumberOfPlayers() != null) {
            gameToUpdate.setNumberOfPlayers(game.getNumberOfPlayers());
        }
        if(game.getGenre() != null) {
            gameToUpdate.setGenre(game.getGenre());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(gameRepository.save(gameToUpdate)));
    }

    private Game findGame(int id) {
        return gameRepository.findById(id).orElse(null);
    }

    private boolean containsNull(Game game) {
        return game.getTitle() == null || game.getGameStudio() == null || game.getAgeRating() == null || game.getNumberOfPlayers() == null || game.getGenre() == null;
    }
}


