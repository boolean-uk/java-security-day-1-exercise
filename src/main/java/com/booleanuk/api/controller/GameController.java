package com.booleanuk.api.controller;

import com.booleanuk.api.exceptions.CustomDataNotFoundException;
import com.booleanuk.api.exceptions.CustomParamaterConstraintException;
import com.booleanuk.api.model.Game;
import com.booleanuk.api.model.Library;
import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.GameRepository;
import com.booleanuk.api.repository.LibraryRepository;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.response.SuccessResponse;
import com.booleanuk.api.util.DateCreater;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("libraries/{libraryId}/games")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired LibraryRepository libraryRepository;



    @PostMapping
    public ResponseEntity<Response<?>> createGame(@PathVariable (name = "libraryId") int libraryId, @RequestBody Game game) {

        Library library = getALibrary(libraryId);

        Game game1 = new Game(game.getTitle(), game.getGameStudio(),game.getAgeRating(),game.getGenre(), library);



        library.getGames().add(game1);
        game1.setLoans(new ArrayList<>());
        game1.setLoansHistory(new ArrayList<>());
        checkValidInput(game1);

        this.gameRepository.save(game1);

        return new ResponseEntity<>(new SuccessResponse(game1), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<Game>> getGames(@PathVariable (name = "libraryId") int libraryId) {
        Library library = getALibrary(libraryId);

        List<Game> games = library.getGames();

        return new ResponseEntity<>(games, HttpStatus.OK);
    }



    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getSpecificGame(@PathVariable (name = "libraryId") int libraryId, @PathVariable (name = "id") int id) {
        Library library = getALibrary(libraryId);

        if(library.getGames() == null) {
            library.setGames(new ArrayList<>());
            throw new CustomDataNotFoundException("Game not found");
        } else {
            Game game1 = library.getGames().get(id-1);
            if(game1 != null) {
                return new ResponseEntity<>(new SuccessResponse(game1), HttpStatus.OK);
            }
        }


       throw new CustomDataNotFoundException("Game not found");

    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateGame(@PathVariable (name = "libraryId") int libraryId, @PathVariable (name = "id") int id, @RequestBody Game game) {
        Library library = getALibrary(libraryId);


        if(library.getGames() == null) {
            library.setGames(new ArrayList<>());
        } else {
            Game game1 = library.getGames().get(id);
            if(game1 != null) {
                game1.setTitle(game.getTitle());
                game1.setLibrary(game.getLibrary());
                game1.setGameStudio(game.getGameStudio());
                game1.setGenre(game.getGenre());
                game1.setAgeRating(game.getAgeRating());

                this.checkValidInput(game1);

                return new ResponseEntity<>(new SuccessResponse(game1), HttpStatus.CREATED);

            }

        }
        throw new CustomDataNotFoundException("Game not found");

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteGame(@PathVariable (name = "libraryId") int libraryId, @PathVariable (name = "id") int id) {
        Game game = this.getAGame(id);

        Library library = getALibrary(libraryId);


        if(library.getGames() == null) {
            library.setGames(new ArrayList<>());
        } else {
            Game game1 = library.getGames().get(id);
            if(game1 != null) {

                library.getGames().remove(game1);

            }

        }
        throw new CustomDataNotFoundException("Game not found");

    }

    private Game getAGame(int id) {
        return this.gameRepository.findById(id).orElseThrow(() -> new CustomDataNotFoundException("No game with that ID found"));
    }


    private Library getALibrary(int id) {
        return this.libraryRepository.findById(id).orElseThrow(() -> new CustomDataNotFoundException("No library with that ID found"));
    }

    private void checkValidInput(Game game) {
        if(game.getGameStudio() == null || game.getGenre() == null || game.getTitle() == null) {
            throw new CustomParamaterConstraintException("Bad request");
        }
    }


}
