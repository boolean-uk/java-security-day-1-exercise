package com.booleanuk.api.controller;

import com.booleanuk.api.model.VideoGame;
import com.booleanuk.api.repository.VideoGameRepository;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.response.VideoGameListResponse;
import com.booleanuk.api.response.VideoGameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("video_games")
public class VideoGameController {

    @Autowired
    private VideoGameRepository videoGameRepository;


    @GetMapping
    public ResponseEntity<VideoGameListResponse> getAllVideoGames() {
        VideoGameListResponse videoGameListResponse = new VideoGameListResponse();
        videoGameListResponse.set(this.videoGameRepository.findAll());

        return ResponseEntity.ok(videoGameListResponse);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getVideoGameByID(@PathVariable int id) {
        VideoGame videoGame = this.videoGameRepository.findById(id).orElse(null);
        if (videoGame == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        VideoGameResponse videoGameResponse = new VideoGameResponse();
        videoGameResponse.set(videoGame);
        return ResponseEntity.ok(videoGameResponse);
    }


    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody VideoGame videoGame) {

        this.videoGameRepository.save(videoGame);

        VideoGameResponse videoGameResponse = new VideoGameResponse();
        videoGameResponse.set(videoGame);

        return ResponseEntity.ok(videoGameResponse);
    }


    @PutMapping("/{id}")
    public ResponseEntity<VideoGame> updateVideoGame(@PathVariable int id, @RequestBody VideoGame videoGame) {
        VideoGame videoGameToUpdate = this.videoGameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "VideoGame Not Found by ID"));

        videoGameToUpdate.setTitle(videoGame.getTitle());
        videoGameToUpdate.setGameStudio(videoGame.getGameStudio());
        videoGameToUpdate.setAgeRating(videoGame.getAgeRating());
        videoGameToUpdate.setNumPlayers(videoGame.getNumPlayers());
        videoGameToUpdate.setGenre(videoGame.getGenre());

        return new ResponseEntity<VideoGame>(videoGameRepository.save(videoGameToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<VideoGame> deleteVideoGame(@PathVariable int id) {
        VideoGame videoGameToDelete = this.videoGameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "VideoGame Not Found by ID"));

        this.videoGameRepository.delete(videoGameToDelete);

        return ResponseEntity.ok(videoGameToDelete);
    }

}


