package com.booleanuk.api.controller;

import com.booleanuk.api.models.Game;
import com.booleanuk.api.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("games")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @PostMapping
    public ResponseEntity<Game> create(@RequestBody Game game) {
        return new ResponseEntity<>(this.gameRepository.save(game), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Game>> getGames(@RequestParam(required = false) String title) {
        if (title == null) {
            return ResponseEntity.ok(gameRepository.findAll());
        } else {
            List<Game> games = gameRepository.findAllByTitle(title);
            if (games.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No games found with that name");
            }
            return ResponseEntity.ok(games);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getOneVideoGame(@PathVariable int id) {
        Game videoGame = this.gameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video Game with ID " + id + " not found."));
        return ResponseEntity.ok(videoGame);
    }


    @PutMapping("{id}")
    public ResponseEntity<Game> updateGame(@PathVariable int id, @RequestBody Game game) {
        Game existingCustomer = gameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));

        existingCustomer.setAgeRating(game.getAgeRating());
        existingCustomer.setTitle(game.getTitle());
        existingCustomer.setNumberOfPlayers(game.getNumberOfPlayers());

        Game updatedGame = gameRepository.save(existingCustomer);
        return ResponseEntity.ok(updatedGame);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Game> deleteGame(@PathVariable int id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));

        gameRepository.delete(game);
        return ResponseEntity.ok(game);
    }

}
