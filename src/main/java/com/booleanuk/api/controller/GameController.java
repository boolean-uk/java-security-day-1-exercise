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

@RestController
@RequestMapping("/games")
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
    public ResponseEntity<Response<?>> createGame(@RequestBody Game game) {
        this.gameRepository.save(game);
        GameResponse gameResponse = new GameResponse();
        gameResponse.set(game);
        return new ResponseEntity<>(gameResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateGame(@PathVariable int id, @RequestBody Game game) {
        Game gameToUpdate = this.gameRepository.findById(id).orElse(null);
        if (gameToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        gameToUpdate.setYear(game.getYear());
        gameToUpdate.setGenre(game.getGenre());
        gameToUpdate.setPublisher(game.getPublisher());
        gameToUpdate.setTitle(game.getTitle());
        gameToUpdate.setLoans(game.getLoans());

        this.gameRepository.save(gameToUpdate);
        GameResponse gameResponse = new GameResponse();
        gameResponse.set(gameToUpdate);
        return new ResponseEntity<>(gameResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Response<?>> deleteGame(@PathVariable int id) {
        Game game = this.gameRepository.findById(id).orElse(null);
        if (game == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        GameResponse gameResponse = new GameResponse();
        gameResponse.set(game);
        this.gameRepository.delete(game);
        return ResponseEntity.ok(gameResponse);
    }
}
