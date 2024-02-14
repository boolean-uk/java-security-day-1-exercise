package com.booleanuk.api.controller;

import com.booleanuk.api.model.Videogame;
import com.booleanuk.api.repository.VideogameRespository;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.response.VideogameListResponse;
import com.booleanuk.api.response.VideogameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/videogames")
public class VideogameController {
    @Autowired
    private VideogameRespository videogameRespository;

    @GetMapping
    public ResponseEntity<VideogameListResponse> getAllVideogames() {
        VideogameListResponse videogameListResponse = new VideogameListResponse();

        videogameListResponse.set(this.videogameRespository.findAll());

        return ResponseEntity.ok(videogameListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getVideogameById(@PathVariable int id) {
        Videogame videogame = this.videogameRespository.findById(id).orElse(null);

        if (videogame == null) {
            ErrorResponse error = new ErrorResponse();

            error.set("not found");

            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        VideogameResponse videogameResponse = new VideogameResponse();
        videogameResponse.set(videogame);

        return ResponseEntity.ok(videogameResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createVideogame(@RequestBody Videogame videogame) {
        if (videogame.getTitle() == null
                || videogame.getGameStudio() == null
                || videogame.getAgeRating() == null
                || videogame.getNumberOfPlayers() < 0
                || videogame.getGenre() == null) {

            return new ResponseEntity<>(new ErrorResponse(), HttpStatus.BAD_REQUEST);
        }

        Videogame newVideogame = this.videogameRespository.save(videogame);

        VideogameResponse videogameResponse = new VideogameResponse();
        videogameResponse.set(newVideogame);

        return new ResponseEntity<>(videogameResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateVideoGame(@PathVariable int id, @RequestBody Videogame videogame) {
        if (videogame.getTitle() == null
                || videogame.getGameStudio() == null
                || videogame.getAgeRating() == null
                || videogame.getNumberOfPlayers() < 0
                || videogame.getGenre() == null) {

            return new ResponseEntity<>(new ErrorResponse(), HttpStatus.BAD_REQUEST);
        }

        Videogame videogameToBeUpdated = this.videogameRespository.findById(id).orElse(null);

        if (videogameToBeUpdated == null) {
            ErrorResponse error = new ErrorResponse();

            error.set("not found");

            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        videogameToBeUpdated.setTitle(videogame.getTitle());
        videogameToBeUpdated.setGameStudio(videogame.getGameStudio());
        videogameToBeUpdated.setAgeRating(videogame.getAgeRating());
        videogameToBeUpdated.setNumberOfPlayers(videogame.getNumberOfPlayers());
        videogameToBeUpdated.setGenre(videogame.getGenre());

        Videogame updatedVideogame = this.videogameRespository.save(videogameToBeUpdated);

        VideogameResponse videogameResponse = new VideogameResponse();
        videogameResponse.set(updatedVideogame);

        return new ResponseEntity<>(videogameResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteVideogame(@PathVariable int id) {
        Videogame videogameToBeDeleted = this.videogameRespository.findById(id).orElse(null);

        if (videogameToBeDeleted == null) {
            ErrorResponse error = new ErrorResponse();

            error.set("not found");

            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        this.videogameRespository.deleteById(id);

        VideogameResponse videogameResponse = new VideogameResponse();
        videogameResponse.set(videogameToBeDeleted);

        return ResponseEntity.ok(videogameResponse);
    }
}
