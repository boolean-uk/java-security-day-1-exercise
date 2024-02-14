package com.booleanuk.api.controller;

import com.booleanuk.api.model.User;
import com.booleanuk.api.model.VideoGame;
import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.api.repository.VideoGameRepository;
import com.booleanuk.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/videogames")
public class VideoGameController {

    @Autowired
    private VideoGameRepository videoGameRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<VideoGameListResponse> getAllVideoGames() {
        List<VideoGame> videoGames = this.videoGameRepository.findAll();
        VideoGameListResponse videoGameListResponse = new VideoGameListResponse();
        videoGameListResponse.set(videoGames);
        return ResponseEntity.ok(videoGameListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getVideoGameById(@PathVariable int id) {
        VideoGame returnVideoGame = this.videoGameRepository.findById(id).orElse(null);
        if (returnVideoGame == null ) {
            ErrorResponse error = new ErrorResponse();
            error.set("No video games matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        VideoGameResponse videoGameResponse = new VideoGameResponse();
        videoGameResponse.set(returnVideoGame);
        return ResponseEntity.ok(videoGameResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createVideoGame(@RequestBody VideoGame videoGame) {
        if (videoGame.getTitle() == null || videoGame.getGenre() == null
            || videoGame.getDeveloper() == null || videoGame.getPublisher() == null
            || videoGame.getYear() < 1958 || videoGame.getAgeRating() < 3) { //First game ever created is thought to be created in 1958. Lowest age rating on video games is 3.
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create the video game, please check all required fields");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        User tempUser = this.userRepository.findById(videoGame.getUser().getId()).orElse(null);

        if(tempUser == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No users matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        videoGame.setUser(tempUser);

        VideoGame createdVideoGame = this.videoGameRepository.save(videoGame);

        VideoGameResponse videoGameResponse = new VideoGameResponse();
        videoGameResponse.set(createdVideoGame);
        return new ResponseEntity<>(videoGameResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateVideoGame(@PathVariable int id, @RequestBody VideoGame videoGame) {
        if (videoGame.getTitle() == null || videoGame.getGenre() == null
                || videoGame.getDeveloper() == null || videoGame.getPublisher() == null
                || videoGame.getYear() < 1958 || videoGame.getAgeRating() < 3) { //First game ever created is thought to be created in 1958. Lowest age rating on video games is 3.
            ErrorResponse error = new ErrorResponse();
            error.set("Could not update the author's details, please check all required fields");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        User tempUser = this.userRepository.findById(videoGame.getUser().getId()).orElse(null);

        if(tempUser == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No users matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        videoGame.setUser(tempUser);

        VideoGame videoGameToUpdate = this.videoGameRepository.findById(id).orElse(null);

        if(videoGameToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No video games matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        videoGameToUpdate.setTitle(videoGame.getTitle());
        videoGameToUpdate.setGenre(videoGame.getGenre());
        videoGameToUpdate.setDeveloper(videoGame.getDeveloper());
        videoGameToUpdate.setPublisher(videoGame.getPublisher());
        videoGameToUpdate.setYear(videoGame.getYear());
        videoGameToUpdate.setAgeRating(videoGame.getAgeRating());
        videoGameToUpdate.setUser(videoGame.getUser());

        VideoGameResponse videoGameResponse = new VideoGameResponse();
        videoGameResponse.set(videoGameToUpdate);
        return new ResponseEntity<>(videoGameResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteUser(@PathVariable int id) {
        VideoGame videoGameToDelete = this.videoGameRepository.findById(id).orElse(null);

        if(videoGameToDelete == null ) {
            ErrorResponse error = new ErrorResponse();
            error.set("No video games matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        this.videoGameRepository.delete(videoGameToDelete);

        VideoGameResponse videoGameResponse = new VideoGameResponse();
        videoGameResponse.set(videoGameToDelete);
        return ResponseEntity.ok(videoGameResponse);
    }

}
