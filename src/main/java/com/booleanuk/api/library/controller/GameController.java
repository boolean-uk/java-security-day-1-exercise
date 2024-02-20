package com.booleanuk.api.library.controller;

import com.booleanuk.api.library.model.Game;
import com.booleanuk.api.library.repository.GameRepository;
import com.booleanuk.api.library.response.ErrorResponse;
import com.booleanuk.api.library.response.GameListResponse;
import com.booleanuk.api.library.response.GameResponse;
import com.booleanuk.api.library.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("games")
public class GameController {
    @Autowired
    private GameRepository gameRepository;

    @GetMapping
    public ResponseEntity<GameListResponse> getAllGame() {
        GameListResponse gameListResponse = new GameListResponse();
        gameListResponse.set(this.gameRepository.findAll());
        return ResponseEntity.ok(gameListResponse);
    }

    private Game getAGame(int id) {
        return this.gameRepository.findById(id).orElse(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getGameById(@PathVariable int id) {
        Game game = this.getAGame(id);
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
    public ResponseEntity<Response<?>> createABook(@RequestBody Game game) {
        if(game.getTitle() == null || game.getGameStudio() == null || game.getAgeRating() == null || game.getNumberOfPlayer() == null || game.getGenre() == null){
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        Game savedGame = this.gameRepository.save(game);
        GameResponse gameResponse = new GameResponse();
        gameResponse.set(savedGame);

        return new ResponseEntity<>(gameResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateGame(@PathVariable int id, @RequestBody Game game){
        Game gameToUpdate = this.getAGame(id);
        if(gameToUpdate == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        if(game.getTitle() == null || game.getGameStudio() == null || game.getAgeRating() == null || game.getNumberOfPlayer() == null || game.getGenre() == null){
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        gameToUpdate.setTitle(game.getTitle());
        gameToUpdate.setGameStudio(game.getGameStudio());
        gameToUpdate.setAgeRating(game.getAgeRating());
        gameToUpdate.setNumberOfPlayer(game.getNumberOfPlayer());
        gameToUpdate.setGenre(game.getGenre());
        Game updatedGame = this.gameRepository.save(gameToUpdate);

        GameResponse gameResponse = new GameResponse();
        gameResponse.set(updatedGame);
        return new ResponseEntity<>(gameResponse, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteGame (@PathVariable int id){
        Game gameToDelete = this.getAGame(id);
        if(gameToDelete == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.gameRepository.delete(gameToDelete);

        GameResponse gameResponse = new GameResponse();
        gameResponse.set(gameToDelete);
        return ResponseEntity.ok(gameResponse);
    }

}
