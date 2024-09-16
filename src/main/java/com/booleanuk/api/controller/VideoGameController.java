package com.booleanuk.api.controller;

import com.booleanuk.api.model.VideoGame;
import com.booleanuk.api.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/videogames")
public class VideoGameController {

    @Autowired
    private VideoGameRepository videoGameRepository;

    @GetMapping
    public List<VideoGame> getVideoGames(){
        return videoGameRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoGame> getVideoGameById(@PathVariable(value = "id") int id){
        VideoGame videoGame = videoGameRepository.findById(id).orElse(null);
        if(videoGame == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video Game not found");
        }
        return ResponseEntity.ok(videoGame);
    }
    @PostMapping
    public ResponseEntity<VideoGame> createVideoGame(@RequestBody VideoGame videoGame){
        videoGameRepository.save(videoGame);
        return ResponseEntity.status(HttpStatus.CREATED).body(videoGame);
    }
    @PutMapping("/{id}")
    public ResponseEntity<VideoGame> updateVideoGame(@PathVariable(value = "id") int id, @RequestBody VideoGame videoGame){
        VideoGame existingVideoGame = videoGameRepository.findById(id).orElse(null);
        if(existingVideoGame == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video Game not found");
        }
        existingVideoGame.setTitle(videoGame.getTitle());
        existingVideoGame.setGameStudio(videoGame.getGameStudio());
        existingVideoGame.setAgeRating(videoGame.getAgeRating());
        existingVideoGame.setGenre(videoGame.getGenre());
        videoGameRepository.save(existingVideoGame);
        return ResponseEntity.status(HttpStatus.CREATED).body(existingVideoGame);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<VideoGame> deleteVideoGame(@PathVariable(value = "id") int id){
        VideoGame existingVideoGame = videoGameRepository.findById(id).orElse(null);
        if(existingVideoGame == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video Game not found");
        }
        videoGameRepository.delete(existingVideoGame);
        return ResponseEntity.ok(existingVideoGame);
    }
}
