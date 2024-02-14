package com.booleanuk.api.Controllers;

import com.booleanuk.api.Models.VideoGame;
import com.booleanuk.api.Responses.ErrorResponse;
import com.booleanuk.api.Responses.Response;
import com.booleanuk.api.Repositories.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("videogames")
public class VideoGameController {
    @Autowired
    private VideoGameRepository repo;

    @GetMapping
    public ResponseEntity<Response<List<VideoGame>>> getAll(){
        Response<List<VideoGame>> videoGamesResponse = new Response<>();
        videoGamesResponse.set(repo.findAll());

        return ResponseEntity.ok(videoGamesResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getOne(@PathVariable int id){
        VideoGame videoGame = repo.findById(id).orElse(null);

        if (videoGame == null){
            ErrorResponse notFound = new ErrorResponse();
            notFound.set("not found");

            return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
        }

        Response<VideoGame> videoGameResponse = new Response<>();
        videoGameResponse.set(videoGame);

        return ResponseEntity.ok(videoGameResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> add(@RequestBody VideoGame videoGame){
        if (videoGame.getTitle() == null ||
                videoGame.getGameStudio() == null ||
                videoGame.getAgeRating() == null ||
                videoGame.getNumPlayers() <= 0 ||
                videoGame.getGenre() == null){
            ErrorResponse badRequest = new ErrorResponse();
            badRequest.set("bad request");

            return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
        }

        videoGame.setAvailable(true);
        repo.save(videoGame);
        Response<VideoGame> videoGameResponse = new Response<>();
        videoGameResponse.set(videoGame);

        return ResponseEntity.ok(videoGameResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody VideoGame videoGame){
        if (videoGame.getTitle() == null ||
                videoGame.getGameStudio() == null ||
                videoGame.getAgeRating() == null ||
                videoGame.getNumPlayers() <= 0 ||
                videoGame.getGenre() == null){
            ErrorResponse badRequest = new ErrorResponse();
            badRequest.set("bad request");

            return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
        }

        VideoGame toUpdate = repo.findById(id).orElse(null);

        if (toUpdate == null){
            ErrorResponse notFound = new ErrorResponse();
            notFound.set("not found");

            return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
        }

        Optional.ofNullable(videoGame.getTitle()).ifPresent(title -> toUpdate.setTitle(title));
        Optional.ofNullable(videoGame.getGameStudio()).ifPresent(gameStudio -> toUpdate.setGameStudio(gameStudio));
        Optional.ofNullable(videoGame.getAgeRating()).ifPresent(ageRating -> toUpdate.setAgeRating(ageRating));
        Optional.of(videoGame.getNumPlayers()).ifPresent(numPlayers -> toUpdate.setNumPlayers(numPlayers));
        Optional.ofNullable(videoGame.getGenre()).ifPresent(genre -> toUpdate.setGenre(genre));
        repo.save(toUpdate);

        Response<VideoGame> videoGameResponse = new Response<>();
        videoGameResponse.set(toUpdate);

        return new ResponseEntity<>(videoGameResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id){
        VideoGame toDelete = repo.findById(id).orElse(null);

        if (toDelete == null){
            ErrorResponse notFound = new ErrorResponse();
            notFound.set("not found");

            return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
        }

        repo.delete(toDelete);
        Response<VideoGame> videoGameResponse = new Response<>();
        videoGameResponse.set(toDelete);

        return ResponseEntity.ok(videoGameResponse);
    }

}
