package com.booleanuk.api.controller;

import com.booleanuk.api.model.Videogame;
import com.booleanuk.api.repository.VideogameRepository;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.response.VideogameListResponse;
import com.booleanuk.api.response.VideogameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videogames")
public class VideogameController {
    @Autowired
    VideogameRepository videogameRepository;
    
    @GetMapping
    public ResponseEntity<VideogameListResponse> getAll() {
        VideogameListResponse videogameListResponse = new VideogameListResponse();
        videogameListResponse.set(this.videogameRepository.findAll());
        return ResponseEntity.ok(videogameListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getById(@PathVariable int id) {
        VideogameResponse videogameResponse = new VideogameResponse();
        Videogame videogame = this.videogameRepository.findById(id).orElse(null);
        videogameResponse.set(videogame);
        return ResponseEntity.ok(videogameResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Videogame videogame) {
        VideogameResponse videogameResponse = new VideogameResponse();
        Videogame createdVideogame = this.videogameRepository.save(videogame);;
        Optional.ofNullable(videogame.getTitle()).ifPresent((title) -> createdVideogame.setTitle(title));
        Optional.ofNullable(videogame.getStudio()).ifPresent((studio) -> createdVideogame.setStudio(studio));
        Optional.ofNullable(videogame.getAgeRating()).ifPresent((agerating) -> createdVideogame.setAgeRating(agerating));
        Optional.ofNullable(videogame.getGenre()).ifPresent((genre) -> createdVideogame.setGenre(genre));
        videogameResponse.set(createdVideogame);
        this.videogameRepository.save(createdVideogame);
        return new ResponseEntity<>(videogameResponse, HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody Videogame videogame) {
        Videogame gameToUpdate = this.videogameRepository.findById(id).orElse(null);
        if (gameToUpdate == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        VideogameResponse videogameResponse = new VideogameResponse();
        videogameResponse.set(gameToUpdate);
        return new ResponseEntity<>(videogameResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id) {
        Videogame gameToDelete = this.videogameRepository.findById(id).orElse(null);
        if (gameToDelete == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        VideogameResponse videogameResponse = new VideogameResponse();
        videogameResponse.set(gameToDelete);
        return new ResponseEntity<>(videogameResponse, HttpStatus.CREATED);
    }


}
