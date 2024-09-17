package com.booleanuk.api.controllers;

import com.booleanuk.api.models.VideoGame;
import com.booleanuk.api.repositories.VideoGameRepository;
import com.booleanuk.api.responses.ErrorResponse;
import com.booleanuk.api.responses.Response;
import com.booleanuk.api.responses.VideoGameListResponse;
import com.booleanuk.api.responses.VideoGameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("games")
public class VideoGameController {
    @Autowired
    private VideoGameRepository repository;

    @PostMapping
    public ResponseEntity<VideoGameResponse> create(@RequestBody VideoGame game) {
        this.repository.save(game);
        VideoGameResponse response = new VideoGameResponse();
        response.set(game);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<VideoGameListResponse> getAll() {
        VideoGameListResponse response = new VideoGameListResponse();
        response.set(this.repository.findAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getSpecific(@PathVariable int id) {
        VideoGame game = this.repository.findById(id).orElse(null);
        if (game == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("A game with this id was not found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        VideoGameResponse response = new VideoGameResponse();
        response.set(game);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody VideoGame game) {
        VideoGame originalGame = this.repository.findById(id).orElse(null);
        if (originalGame == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        game.setId(id);
        try {
            this.repository.save(game);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        VideoGameResponse response = new VideoGameResponse();
        response.set(game);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id) {
        VideoGame game = this.repository.findById(id).orElse(null);
        if (game == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.repository.delete(game);
        VideoGameResponse response = new VideoGameResponse();
        response.set(game);
        return ResponseEntity.ok(response);
    }
}
