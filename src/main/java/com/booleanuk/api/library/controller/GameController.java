package com.booleanuk.api.library.controller;

import com.booleanuk.api.library.repository.GameRepository;
import com.booleanuk.api.library.response.GameListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("games")
public class GameController {
    @Autowired
    private GameRepository gameRepository;

//    @GetMapping
//    public ResponseEntity<GameListResponse>
}
