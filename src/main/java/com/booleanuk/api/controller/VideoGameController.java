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

@RestController
@RequestMapping("videogames")
public class VideoGameController {
    @Autowired
    private VideoGameRepository videoGameRepository;

    @GetMapping
    public ResponseEntity<VideoGameListResponse> getAllBooks() {
        VideoGameListResponse bookListResponse = new VideoGameListResponse();
        bookListResponse.set(this.videoGameRepository.findAll());
        return ResponseEntity.ok(bookListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getBookById(@PathVariable int id) {
        VideoGame book = this.videoGameRepository.findById(id).orElse(null);
        if (book == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        VideoGameResponse bookResponse = new VideoGameResponse();
        bookResponse.set(book);
        return ResponseEntity.ok(bookResponse);
    }
    @PostMapping
    public ResponseEntity<VideoGame> create(@RequestBody VideoGame movie) {
        VideoGame savedMovie = videoGameRepository.save(movie);
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<VideoGame> delete(@PathVariable int id) {
        VideoGame movie = videoGameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        videoGameRepository.delete(movie);
        return ResponseEntity.ok(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoGame> update(@PathVariable int id, @RequestBody VideoGame movieDetails) {
        VideoGame movie = videoGameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        movie.setTitle(movieDetails.getTitle());
        movie.setGenre(movieDetails.getGenre());
        movie.setUser(movieDetails.getUser());
        VideoGame updatedMovie = videoGameRepository.save(movie);
        return ResponseEntity.ok(updatedMovie);
    }
}
