package com.booleanuk.api.library.controller;

import com.booleanuk.api.library.model.Game;
import com.booleanuk.api.library.repository.GameRepository;
import com.booleanuk.api.library.repository.UserRecordRepository;
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
    GameRepository gameRepository;

    @Autowired
    UserRecordRepository userRepository;
    @GetMapping
    public List<Game> getAll(){
        return this.gameRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getOne(@PathVariable int id){
        return new ResponseEntity<>(
                this.gameRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Game> addOne(@RequestBody Game game){

        return new ResponseEntity<>(this.gameRepository.save(game), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> updateOne(@PathVariable int id, @RequestBody Game game){
        Game gameToUpdate = this.gameRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        gameToUpdate.setGameStudio(game.getGameStudio());
        gameToUpdate.setGenre(game.getGenre());
        gameToUpdate.setTitle(game.getTitle());
        gameToUpdate.setAgeRating(game.getAgeRating());

        return new ResponseEntity<>(this.gameRepository.save(gameToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Game> deleteOne(@PathVariable int id){
        Game gameToDelete = this.gameRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        try{
            this.gameRepository.delete(gameToDelete);
            return new ResponseEntity<>(gameToDelete, HttpStatus.OK);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
