package com.booleanuk.api.controller;

import com.booleanuk.api.models.Game;
import com.booleanuk.api.repositories.GameRepository;
import com.booleanuk.api.responses.ErrorResponse;
import com.booleanuk.api.responses.GameListResponse;
import com.booleanuk.api.responses.GameResponse;
import com.booleanuk.api.responses.Response;
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
    public ResponseEntity<Response<?>> getAllGames(){
        List<Game> games = this.gameRepository.findAll();

        GameListResponse gameListResponse = new GameListResponse();
        gameListResponse.set(games);

        return ResponseEntity.ok(gameListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getGame(@PathVariable int id) {
        Game game = this.gameRepository.findById(id).orElse(null);

        if (game == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No game with that id found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        GameResponse gameResponse = new GameResponse();
        gameResponse.set(game);

        return ResponseEntity.ok(gameResponse);

    }

    @PostMapping
    public ResponseEntity<Response<?>> createGame(@RequestBody Game game){
        if (game.getTitle() == null || game.getGameStudio() == null || game.getGenre() == null){
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create a new game, please check all fields are correct");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        gameRepository.save(game);
        GameResponse gameResponse = new GameResponse();
        gameResponse.set(game);

        return new ResponseEntity<>(gameResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateGame(@PathVariable int id, @RequestBody Game game){
        if (game.getTitle() == null || game.getGameStudio() == null || game.getGenre() == null){
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create a new movie, please check all fields are correct");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Game gameToUpdate = this.gameRepository.findById(id).orElse(null);
        if (gameToUpdate == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No games with that id to update");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        gameToUpdate.setTitle(game.getTitle());
        gameToUpdate.setGameStudio(game.getGameStudio());
        gameToUpdate.setGenre(game.getGenre());
        gameToUpdate.setNumOfPlayers(game.getNumOfPlayers());
        gameToUpdate.setAge(game.getAge());
        this.gameRepository.save(gameToUpdate);

        GameResponse gameResponse = new GameResponse();
        gameResponse.set(gameToUpdate);

        return new ResponseEntity<>(gameResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteGame(@PathVariable int id){
        Game game = this.gameRepository.findById(id).orElse(null);
        if (game == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No games found with that id to remove");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

        }
        gameRepository.delete(game);

        GameResponse gameResponse = new GameResponse();
        gameResponse.set(game);

        return new ResponseEntity<>(gameResponse, HttpStatus.CREATED);
    }
}
