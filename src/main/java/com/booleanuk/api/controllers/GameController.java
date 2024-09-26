package com.booleanuk.api.controllers;

import com.booleanuk.api.models.BorrowGame;
import com.booleanuk.api.models.Game;
import com.booleanuk.api.repositories.BorrowGameRepository;
import com.booleanuk.api.repositories.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("games")
@AllArgsConstructor
public class GameController {
    private GameRepository gameRepository;
    private BorrowGameRepository borrowGameRepository;

    @GetMapping
    public List<Game> getAll() {
        return gameRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Game add(@RequestBody Game game) {
        if (game.haveNullFields()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return gameRepository.save(game);
    }

    @GetMapping("{id}")
    public Game getById(@PathVariable int id) {
        if (gameRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return gameRepository.findById(id).get();
    }

    @GetMapping("{id}/history")
    public List<BorrowGame> getBorrowedGames(@PathVariable int id) {
        if (gameRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Game game = gameRepository.findById(id).get();
        return borrowGameRepository.findAllByGame(game).get();
    }

    @PutMapping("{id}")
    public Game update(@PathVariable int id, @RequestBody Game game) {
        if (gameRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Game gameToUpdate = gameRepository.findById(id).get();
        gameToUpdate.setDeveloper(game.getDeveloper() != null ? game.getDeveloper() : gameToUpdate.getDeveloper());
        gameToUpdate.setTitle(game.getTitle() != null ? game.getTitle() : gameToUpdate.getTitle());
        gameToUpdate.setRating(game.getRating() != null ? game.getRating() : gameToUpdate.getRating());
        gameToUpdate.setPublisher(game.getPublisher() != null ? game.getPublisher() : gameToUpdate.getPublisher());
        gameToUpdate.setGenre(game.getGenre() != null ? game.getGenre() : gameToUpdate.getGenre());

        return gameRepository.save(gameToUpdate);
    }

    @DeleteMapping("{id}")
    public Game delete(@PathVariable int id) {
        if (gameRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Game gameToDelete = gameRepository.findById(id).get();
        gameRepository.delete(gameToDelete);
        return gameToDelete;
    }
}
