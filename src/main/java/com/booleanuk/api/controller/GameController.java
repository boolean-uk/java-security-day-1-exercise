package com.booleanuk.api.controller;
import com.booleanuk.api.model.Game;
import com.booleanuk.api.repository.GameRepository;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.response.GameListResponse;
import com.booleanuk.api.response.GameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("games")
public class GameController {
    @Autowired
    private GameRepository videoGameRepository;

    @GetMapping
    public ResponseEntity<GameListResponse> getAllVideoGames() {
        GameListResponse gameListResponse = new GameListResponse();
        gameListResponse.set(this.videoGameRepository.findAll());
        return ResponseEntity.ok(gameListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getVideoGameById(@PathVariable int id) {
        Game game = this.videoGameRepository.findById(id).orElse(null);
        if (game == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("videoGame with that ID not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        GameResponse gameResponse = new GameResponse();
        gameResponse.set(game);
        return ResponseEntity.ok(gameResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createVideoGame(@RequestBody Game game){
        if (isInvalidRequest(game)){
            return badRequest();
        }
        Game createdGame = this.videoGameRepository.save(game);
        GameResponse gameResponse = new GameResponse();
        gameResponse.set(createdGame);
        return new ResponseEntity<>(gameResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> UpdateVideoGameById(@PathVariable int id, @RequestBody Game game){

        if (isInvalidRequest(game)) {
            return badRequest();
        }

        Game gameToUpdate = this.getAVideoGame(id);

        if(gameToUpdate == null){
            return notFound();
        }

        gameToUpdate.setTitle(game.getTitle());
        gameToUpdate.setGameStudio(game.getGameStudio());
        gameToUpdate.setGenre(game.getGenre());
        gameToUpdate.setNumberOfPlayers(game.getNumberOfPlayers());
        this.videoGameRepository.save(gameToUpdate);

        GameResponse response = new GameResponse();
        response.set(gameToUpdate);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteVideoGameById(@PathVariable int id) {
        Game gameToDelete = this.getAVideoGame(id);
        if (gameToDelete == null){
            return this.notFound();
        }
        this.videoGameRepository.delete(gameToDelete);
        GameResponse response = new GameResponse();
        response.set(gameToDelete);
        return ResponseEntity.ok(response);
    }

    private Game getAVideoGame(int id){
        return this.videoGameRepository.findById(id).orElse(null);
    }

    private boolean isInvalidRequest(Game game){
        return game.getTitle() == null || game.getGameStudio() == null || game.getGenre() == null || game.getNumberOfPlayers() == 0;
    }

    private ResponseEntity<Response<?>> badRequest(){
        ErrorResponse error = new ErrorResponse();
        error.set("Could not create videoGame, please check all required fields are correct");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Response<?>> notFound(){
        ErrorResponse error = new ErrorResponse();
        error.set("No videoGame with that id were found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
