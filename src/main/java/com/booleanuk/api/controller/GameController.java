package com.booleanuk.api.controller;

import com.booleanuk.api.model.Game;
import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.GameRepository;
import com.booleanuk.api.repository.RentRepository;
import com.booleanuk.api.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentRepository rentRepository;

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

    @GetMapping("/{id}/rents")
    public ResponseEntity<Response<?>> getRents(@PathVariable int id) {
        Game game = this.gameRepository.findById(id).orElse(null);
        if (game == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        RentListResponse rentListResponse = new RentListResponse();
        rentListResponse.set(game.getRents());
        return ResponseEntity.ok(rentListResponse);
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

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateAGame(@PathVariable int id,@RequestBody Game game){
        Game gameToUpdate = this.gameRepository.findById(id).orElse(null);
        if (gameToUpdate ==null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        //Check the fields
        if(game.getTitle().isEmpty() || game.getAgeRating().isEmpty()|| game.getGameStudio().isEmpty() || game.getGenre().isEmpty() || game.getNumPlayers()<=0){
            ErrorResponse error = new ErrorResponse();
            error.set("Fill in required fields");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        gameToUpdate.setTitle(game.getTitle());
        gameToUpdate.setGameStudio(game.getGameStudio());
        gameToUpdate.setGenre(game.getGenre());
        gameToUpdate.setAgeRating(game.getAgeRating());
        gameToUpdate.setNumPlayers(game.getNumPlayers());
        gameToUpdate.setBorrowed(game.isBorrowed());

        this.gameRepository.save(gameToUpdate);
        GameResponse gameResponse = new GameResponse();
        gameResponse.set(gameToUpdate);
        return ResponseEntity.ok(gameResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteAGame(@PathVariable int id){
        Game gameToDelete = this.gameRepository.findById(id).orElse(null);
        if (gameToDelete ==null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        if(gameToDelete.isBorrowed()){
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request, game is currently borrowed");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        this.gameRepository.delete(gameToDelete);
        GameResponse gameResponse = new GameResponse();
        gameResponse.set(gameToDelete);
        return ResponseEntity.ok(gameResponse);}

}
