package com.booleanuk.api.controller;

import com.booleanuk.api.model.ApiResponse;
import com.booleanuk.api.model.Game;
import com.booleanuk.api.repository.GameRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("games")
public class GameController {
    @Autowired
    private final GameRepository repository;

    public GameController(GameRepository gameRepository) {
        this.repository = gameRepository;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create (@RequestBody Game gameDetails) {
        if (!isValidGame(gameDetails)) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not create the specified game, please check that all fields are correct.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Game game = new Game(gameDetails.getTitle(), gameDetails.getRating(), gameDetails.getDescription(), gameDetails.getGenre());

        ApiResponse<Game> response = new ApiResponse<>("success", repository.save(game));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Game>>> readAll() {
        List<Game> games = this.repository.findAll();
        ApiResponse<List<Game>> response = new ApiResponse<>("success", games);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<?>> readGame(@PathVariable int id) {
        Optional<Game> gam = this.repository.findById(id);

        if (gam.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No game with id %d found.", id));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Game game = gam.get();
        ApiResponse<Game> response = new ApiResponse<>("success", game);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<?>> create (@PathVariable int id, @RequestBody Game gameDetails) {
        Optional<Game> gam = this.repository.findById(id);

        if (gam.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No game with id %d found.", id));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        if (!isValidGame(gameDetails)) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not create the specified game, please check that all fields are correct.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Game game = gam.get();
        game.setTitle(gameDetails.getTitle());
        game.setDescription(gameDetails.getDescription());
        game.setRating(gameDetails.getRating());
        game.setGenre(gameDetails.getGenre());

        ApiResponse<Game> response = new ApiResponse<>("success", repository.save(game));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable int id) {
        Optional<Game> gam = this.repository.findById(id);

        if (gam.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No game with id %d found.", id));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Game game = gam.get();
        repository.delete(game);
        ApiResponse<Game> response = new ApiResponse<>("success", game);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private boolean isValidGame(Game game) {
        return !(StringUtils.isBlank(game.getTitle())
                || StringUtils.isBlank(game.getRating())
                || StringUtils.isBlank(game.getDescription())
                || StringUtils.isBlank(game.getGenre()));
    }
}
