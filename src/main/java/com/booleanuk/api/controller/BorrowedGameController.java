package com.booleanuk.api.controller;

import com.booleanuk.api.dto.BorrowedGameDto;
import com.booleanuk.api.model.BorrowedGame;
import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.BorrowedGameRepository;
import com.booleanuk.api.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("borrow")
public class BorrowedGameController {
    @Autowired
    BorrowedGameRepository repository;

    @GetMapping("/users/{userId}")
    public ResponseEntity<Response<List<BorrowedGameDto>>> getAllForUser(@PathVariable int userId) {
        return ResponseEntity.ok(new Response<>(this.repository.findBorrowedGamesByUserId(userId)));
    }

    @GetMapping("/games/{gameId}")
    public ResponseEntity<Response<List<BorrowedGameDto>>> getAllForGame(@PathVariable int gameId) {
        return ResponseEntity.ok(new Response<>(this.repository.findBorrowedGamesByGameId(gameId)));
    }
}
