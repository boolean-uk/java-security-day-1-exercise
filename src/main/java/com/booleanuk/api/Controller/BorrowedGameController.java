package com.booleanuk.api.Controller;


import com.booleanuk.api.Model.BorrowedGame;
import com.booleanuk.api.Repository.BorrowedGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/borrowedGames")
public class BorrowedGameController {

    @Autowired
    private BorrowedGameRepository borrowedGameRepository;


    @PostMapping
    public ResponseEntity<BorrowedGame> createBorrowedGame(@RequestBody BorrowedGame borrowedGame) {
        BorrowedGame createdBorrowedGame = borrowedGameRepository.save(borrowedGame);
        return new ResponseEntity<>(createdBorrowedGame, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<BorrowedGame>> getAllBorrowedGames() {
        List<BorrowedGame> borrowedGames = borrowedGameRepository.findAll();
        return new ResponseEntity<>(borrowedGames, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BorrowedGame> getBorrowedGameById(@PathVariable Long id) {
        Optional<BorrowedGame> borrowedGame = borrowedGameRepository.findById(id);
        return borrowedGame.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BorrowedGame> updateBorrowedGame(@PathVariable Long id, @RequestBody BorrowedGame borrowedGameDetails) {
        Optional<BorrowedGame> existingBorrowedGame = borrowedGameRepository.findById(id);
        if (existingBorrowedGame.isPresent()) {
            BorrowedGame borrowedGame = existingBorrowedGame.get();
            borrowedGame.setGame(borrowedGameDetails.getGame());
            borrowedGame.setBorrowedBy(borrowedGameDetails.getBorrowedBy());
            borrowedGame.setBorrowedDate(borrowedGameDetails.getBorrowedDate());
            borrowedGame.setReturnDate(borrowedGameDetails.getReturnDate());
            borrowedGameRepository.save(borrowedGame);
            return new ResponseEntity<>(borrowedGame, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrowedGame(@PathVariable Long id) {
        Optional<BorrowedGame> borrowedGame = borrowedGameRepository.findById(id);
        if (borrowedGame.isPresent()) {
            borrowedGameRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
