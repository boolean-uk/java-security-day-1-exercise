package com.booleanuk.api.controller;

import com.booleanuk.api.model.Videogame;
import com.booleanuk.api.repository.VideogameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("videogames")
public class VideogameController {

    @Autowired
    private final VideogameRepository repository;

    public VideogameController(VideogameRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getOne(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.repository.findById(id).orElseThrow());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Object> createVideogame(@RequestBody Videogame game) {
        return new ResponseEntity<>(this.repository.save(game),HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Videogame> updateAuthor(@PathVariable("id") Integer id,
                                               @RequestBody Videogame game) {
        Videogame gameToUpdate = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existing game with that ID found")
        );
        gameToUpdate.setTitle(game.getTitle());
        gameToUpdate.setGenre(game.getGenre());
        gameToUpdate.setGame_studio(game.getGame_studio());
        gameToUpdate.setAge_rating(game.getAge_rating());
        return new ResponseEntity<>(this.repository.save(gameToUpdate),
                HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Videogame> deleteAuthor(@PathVariable("id") Integer id) {
        Videogame gameToDelete = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No game with that ID were found")
        );
        this.repository.delete(gameToDelete);
        return ResponseEntity.ok(gameToDelete);
    }


}
