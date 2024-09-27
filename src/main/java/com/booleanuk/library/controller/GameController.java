package com.booleanuk.library.controller;

import com.booleanuk.library.model.Game;
import com.booleanuk.library.repository.GameRepository;
import com.booleanuk.library.response.CustomResponse;
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
    public ResponseEntity<CustomResponse> getAllGames() {
        CustomResponse customResponse = new CustomResponse("success", gameRepository.findAll());
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getGameById(@PathVariable int id) {
        CustomResponse customResponse = new CustomResponse("success", gameRepository.findById(id));
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomResponse> createGame(@RequestBody Game game) {
        gameRepository.save(game);
        CustomResponse customResponse = new CustomResponse("success", gameRepository.findById(game.getId()));
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateGame(@PathVariable int id, @RequestBody Game game) {
        Game existingGame = gameRepository.findById(id).orElse(null);
        if (existingGame == null) {
            CustomResponse customResponse = new CustomResponse("error", "not found");
            return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
        }
        existingGame.setTitle(game.getTitle());
        existingGame.setGameStudio(game.getGameStudio());
        existingGame.setAgeRating(game.getAgeRating());
        existingGame.setGenre(game.getGenre());
        gameRepository.save(existingGame);
        CustomResponse customResponse = new CustomResponse("success", gameRepository.findById(existingGame.getId()));
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteGame(@PathVariable int id) {
        Game existingGame = gameRepository.findById(id).orElse(null);
        if (existingGame == null) {
            CustomResponse customResponse = new CustomResponse("error", "not found");
            return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
        }
        gameRepository.delete(existingGame);
        CustomResponse customResponse = new CustomResponse("success", "deleted");
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }
}
