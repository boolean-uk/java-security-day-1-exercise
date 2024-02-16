package com.booleanuk.api.controller;

import com.booleanuk.api.model.BorrowGames;
import com.booleanuk.api.model.VideoGame;
import com.booleanuk.api.repository.BorrowGamesRepository;
import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.api.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/borrowgames")
public class BorrowGameController {

        @Autowired
        private BorrowGamesRepository borrowGamesRepository;
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private VideoGameRepository videoGameRepository;

        @GetMapping("/user/{id}")
        public ResponseEntity<List<BorrowGames>> getBorrowedGames(@PathVariable int id){
            return ResponseEntity.ok(borrowGamesRepository.findByUser(userRepository.findById(id).get()));
        }
        @GetMapping("/videogame/{id}")
        public ResponseEntity<List<BorrowGames>> getBorrowedUsers(@PathVariable int id){
            return ResponseEntity.ok(borrowGamesRepository.findByVideoGame(videoGameRepository.findById(id).get()));
        }
        @PostMapping("/setGameToUser/{videogame_id}/{user_id}")
        public ResponseEntity<BorrowGames> borrowGame(@PathVariable int user_id, @PathVariable int videogame_id){
            OffsetDateTime now = OffsetDateTime.now();
            BorrowGames borrowGames = new BorrowGames(now.toString(), now.plusDays(7).toString());
            borrowGames.setUser(userRepository.findById(user_id).get());
            borrowGames.setVideoGame(videoGameRepository.findById(videogame_id).get());
            borrowGamesRepository.save(borrowGames);
            return ResponseEntity.ok(borrowGames);
        }
}
