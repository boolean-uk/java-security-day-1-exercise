package com.booleanuk.api.videogame;

import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.GameListResponse;
import com.booleanuk.api.response.GameResponse;
import com.booleanuk.api.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("games")
public class GameController {
    @Autowired
    private GameRepository gameRepository;

    @GetMapping
    public ResponseEntity<Response> getAll() {
        return new ResponseEntity<>(new GameListResponse(this.gameRepository.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getGame(@PathVariable int id) {
        Game game = this.gameRepository
                .findById(id)
                .orElse(null);
        if (game == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("Game not found")), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new GameResponse(game), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Response> createGame(@RequestBody Game game) {

        if(game.getTitle().isEmpty() || game.getGenre().isEmpty() || game.getGameStudio().isEmpty()){
            return new ResponseEntity<>(new ErrorResponse(new Error("Bad Request")), HttpStatus.BAD_REQUEST);
        }

        this.gameRepository.save(game);
        return new ResponseEntity<>(new GameResponse(game), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteGame(@PathVariable int id) {
        Game deleted = this.gameRepository
                .findById(id)
                .orElse(null);

        if (deleted == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("Game not found")), HttpStatus.NOT_FOUND);
        }
        this.gameRepository.delete(deleted);
        return new ResponseEntity<>(new GameResponse(deleted), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateGame (@PathVariable int id, @RequestBody Game game) {

        Game gameToUpdate = this.gameRepository
                .findById(id)
                .orElse(null);

        if (gameToUpdate == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("Game not found")), HttpStatus.NOT_FOUND);
        }

        if(game.getTitle().isEmpty() || game.getGenre().isEmpty() || game.getGameStudio().isEmpty()){
            return new ResponseEntity<>(new ErrorResponse(new Error("Bad Request")), HttpStatus.BAD_REQUEST);
        }

        gameToUpdate.setTitle(game.getTitle());
        gameToUpdate.setGenre(game.getGenre());
        gameToUpdate.setGameStudio(game.getGameStudio());
        gameToUpdate.setAgeRating(game.getAgeRating());
        gameToUpdate.setNumberOfPlayers(game.getNumberOfPlayers());
        this.gameRepository.save(gameToUpdate);
        return new ResponseEntity<>(new GameResponse(gameToUpdate), HttpStatus.OK);
    }

}
