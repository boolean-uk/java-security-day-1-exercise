package com.booleanuk.api.game;

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

    /*
    @GetMapping
    public ResponseEntity<GenericResponse> getAll() {
        return new ResponseEntity<>(new GamesResponse(this.gameRepository.findAll()), HttpStatus.OK);
    }

     */

    @GetMapping
    public List<Game> getAllGames(){
        return this.gameRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Game> getGameById(@PathVariable int id){
        Game game = null;
        game = this.gameRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No game with that ID found")
        );
        return ResponseEntity.ok(game);
    }

    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody Game game){
        return new ResponseEntity<Game>(this.gameRepository.save(game), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Game> deleteGame(@PathVariable int id){
        Game gameToDelete = this.gameRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No game with that ID found")
        );
        this.gameRepository.delete(gameToDelete);
        return ResponseEntity.ok(gameToDelete);
    }

    @PutMapping("{id}")
    public ResponseEntity<Game> updateGame(@PathVariable int id, @RequestBody Game game){
        Game gameToUpdate = this.gameRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No game with that ID found")
        );
        game.setId(gameToUpdate.getId());
        return new ResponseEntity<>(this.gameRepository.save(game), HttpStatus.CREATED);
    }

}
