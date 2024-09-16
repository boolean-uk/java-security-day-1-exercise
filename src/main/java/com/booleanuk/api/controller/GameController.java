package com.booleanuk.api.controller;

import com.booleanuk.api.model.Game;
import com.booleanuk.api.repository.GameRepository;
import com.booleanuk.api.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("games")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @GetMapping
    public ResponseEntity<GameListResponse> getAllGames(){
        GameListResponse gameListResponse = new GameListResponse();
        gameListResponse.set(this.gameRepository.findAll());
        return ResponseEntity.ok(gameListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getBookById(@PathVariable int id){
        Game game = this.gameRepository.findById(id).orElse(null);
        if(game == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        GameResponse gameResponse = new GameResponse();
        gameResponse.set(game);
        return ResponseEntity.ok(gameResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody GameDTO gameDTO){
        Game gameCreated;
        try{
            Game game = new Game();
            game.setTitle(gameDTO.title());
            game.setGameStudio(gameDTO.gameStudio());
            game.setAgeRating(gameDTO.ageRating());
            game.setNumberOfPlayers(gameDTO.numberOfPlayers());
            game.setGenre(gameDTO.genre());
            gameCreated = this.gameRepository.save(game);
        }catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not create new game");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        GameResponse gameResponse = new GameResponse();
        gameResponse.set(gameCreated);
        return new ResponseEntity<>(gameResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody Game game) {
        Game updateGame = this.gameRepository.findById(id).orElse(null);
        if (updateGame == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        try {
            updateGame.setTitle(game.getTitle());
            updateGame.setGameStudio(game.getGameStudio());
            updateGame.setAgeRating(game.getAgeRating());
            updateGame.setNumberOfPlayers(game.getNumberOfPlayers());
            updateGame.setGenre(game.getGenre());
            updateGame = this.gameRepository.save(updateGame);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Cant update the game chosen");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        }
        GameResponse gameResponse = new GameResponse();
        gameResponse.set(updateGame);
        return new ResponseEntity<>(gameResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id){
        Game deleteGame = this.gameRepository.findById(id).orElse(null);
        if(deleteGame == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        this.gameRepository.delete(deleteGame);

        GameResponse gameResponse = new GameResponse();
        gameResponse.set(deleteGame);
        return ResponseEntity.ok(gameResponse);
    }

}
