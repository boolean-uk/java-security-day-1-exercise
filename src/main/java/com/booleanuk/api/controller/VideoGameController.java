package com.booleanuk.api.controller;

import com.booleanuk.api.model.VideoGame;
import com.booleanuk.api.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/videogames")
public class VideoGameController {
	@Autowired

	private VideoGameRepository videoGameRepository;


	@GetMapping
	public ResponseEntity<List<VideoGame>> getAllVideoGames() {
		List<VideoGame> videoGames = videoGameRepository.findAll();
		return new ResponseEntity<>(videoGames, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<VideoGame> getVideoGameById(@PathVariable int id) {
		VideoGame videoGame = videoGameRepository.findById(id).orElse(null);
		if (videoGame != null) {
			return new ResponseEntity<>(videoGame, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<VideoGame> addVideoGame(@RequestBody VideoGame videoGame) {
		VideoGame newVideoGame = videoGameRepository.save(videoGame);
		return new ResponseEntity<>(newVideoGame, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<VideoGame> updateVideoGame(@PathVariable int id, @RequestBody VideoGame videoGame) {
		VideoGame updatedVideoGame = videoGameRepository.findById(id).orElse(null);
		if (updatedVideoGame != null) {

			if (videoGame.getTitle() != null) {
				updatedVideoGame.setTitle(videoGame.getTitle());
			}
			if (videoGame.getGameStudio() != null) {
				updatedVideoGame.setGameStudio(videoGame.getGameStudio());
			}
			if (videoGame.getGenre() != null) {
				updatedVideoGame.setGenre(videoGame.getGenre());
			}
			if (videoGame.getAgeRating() != null) {
				updatedVideoGame.setAgeRating(videoGame.getAgeRating());
			}
			if (videoGame.getNumberOfPlayers() != 0) {
				updatedVideoGame.setNumberOfPlayers(videoGame.getNumberOfPlayers());
			}
			videoGameRepository.save(updatedVideoGame);
			return new ResponseEntity<>(updatedVideoGame, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<VideoGame> deleteVideoGame(@PathVariable int id) {
		VideoGame videoGame = videoGameRepository.findById(id).orElse(null);
		if (videoGame != null) {
			videoGameRepository.delete(videoGame);
			return new ResponseEntity<>(videoGame, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
