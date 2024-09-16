package com.booleanuk.api.videoGames;

import com.booleanuk.api.Exceptions.BadRequestException;
import com.booleanuk.api.Exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("videoGames")
public class VideoGameController {

    @Autowired
    VideoGameRepository videoGameRepository;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<VideoGame> videoGames = videoGameRepository.findAll();;
        return new ResponseEntity<>(videoGames, HttpStatus.OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOne(@PathVariable int id) {
        VideoGame videoGame = this.findById(id);
        return ResponseEntity.ok(videoGame);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody VideoGame videoGame) {

        try{
            VideoGame videoGameToCreate = this.videoGameRepository.save(videoGame);
            return new ResponseEntity<>(videoGameToCreate, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }

    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody VideoGame videoGame, @PathVariable int id) {
        VideoGame videoGameToUpdate = findById(id);
        try{
            videoGameToUpdate.setTitle(videoGame.getTitle());
            videoGameToUpdate.setGameStudio(videoGame.getGameStudio());
            videoGameToUpdate.setRating(videoGame.getRating());
            videoGameToUpdate.setNumberOfPlayers(videoGame.getNumberOfPlayers());
            videoGameToUpdate.setGenre(videoGame.getGenre());

            this.videoGameRepository.save(videoGameToUpdate);

            return new ResponseEntity<>(videoGameToUpdate, HttpStatus.CREATED);
        }
        catch (Exception e){
            throw new BadRequestException("bad request");
        }

    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        VideoGame videoGame = findById(id);
        this.videoGameRepository.delete(videoGame);
        return new ResponseEntity<>(videoGame, HttpStatus.OK);

    }

    private VideoGame findById(int id) {
        return this.videoGameRepository.findById(id).orElseThrow(
                () -> new NotFoundException("video game not found with ID " + id)
        );

    }
}
