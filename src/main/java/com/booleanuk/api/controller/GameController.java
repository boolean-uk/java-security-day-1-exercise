package com.booleanuk.api.controller;

import com.booleanuk.api.model.Game;
import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.GameRepository;
import com.booleanuk.api.response.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
@RequestMapping("games")
public class GameController {


    @Autowired
    private GameRepository gameRepository;

    @GetMapping
    public ResponseEntity<CustomResponse> getAllGames() {

        if (gameRepository.count() < 1) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("No data found"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }


        CustomResponse response = new CustomResponse("success", this.gameRepository.findAll());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getGameByID(@PathVariable int id) {
        if (!gameRepository.existsById(id)) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("Id is not in database!"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }
        Game game = this.gameRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        CustomResponse res = new CustomResponse("Success", game);
        return ResponseEntity.ok(res);
    }


    @PostMapping
    public ResponseEntity<CustomResponse> createGame(@RequestBody Game game) {

        if (game.getTitle() == null || game.getAgeRating() == 0 || game.getGameStudio() == null || game.getNumPlayers() == 0) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("Check if all fields are correct!"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }
        CustomResponse res = new CustomResponse("success", this.gameRepository.save(game));

        return ResponseEntity.ok(res);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteGameByID(@PathVariable int id) {

        if (!gameRepository.existsById(id)) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("Id is not in database!"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }

        Game game = this.gameRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        this.gameRepository.delete(game);
        User user = new User();
        game.setUser(user);

        CustomResponse res = new CustomResponse("success", game);
        return ResponseEntity.ok(res);


    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateGame(@PathVariable int id, @RequestBody Game game) {

        Game prevgame = this.gameRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found!"));
        Game gameToUpdate = this.gameRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));

        if (game.getTitle() == null) {
            gameToUpdate.setTitle(prevgame.getTitle());
        }
        else {
            gameToUpdate.setTitle(game.getTitle());
        }
        if (game.getAgeRating() == 0) {
            gameToUpdate.setAgeRating(prevgame.getAgeRating());
        }
        else {
            gameToUpdate.setAgeRating(game.getAgeRating());
        }
        if (game.getGameStudio() == null) {
            gameToUpdate.setGameStudio(prevgame.getGameStudio());
        }
        else {
            gameToUpdate.setGameStudio(game.getGameStudio());
        }
        if (game.getNumPlayers() == 0) {
            gameToUpdate.setNumPlayers(prevgame.getNumPlayers());
        }
        else {
            gameToUpdate.setNumPlayers(game.getNumPlayers());
        }


        CustomResponse res = new CustomResponse("success", this.gameRepository.save(gameToUpdate));

        return ResponseEntity.ok(res);
    }

}
