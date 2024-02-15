package com.booleanuk.api.controller;

import com.booleanuk.api.model.Game;
import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.GameRepository;
import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.api.repository.BorrowedGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BorrowedGameController implements BorrowedGameRepository {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    @Autowired
    public BorrowedGameController(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public ResponseEntity<User> borrowGame(int userId, int gameId) {
        System.out.println("User ID: " + userId);
        System.out.println("Game ID: " + gameId);

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            System.out.println("User found: " + user.getUsername());

            Optional<Game> optionalGame = gameRepository.findById(gameId);
            if (optionalGame.isPresent()) {
                Game game = optionalGame.get();
                System.out.println("Game found: " + game.getTitle());

                user.borrowGame(game);
                userRepository.save(user);
                System.out.println("Game borrowed successfully");

                return ResponseEntity.ok(user);
            } else {
                System.out.println("Game not found");
                return ResponseEntity.notFound().build();
            }
        } else {
            System.out.println("User not found");
            return ResponseEntity.notFound().build();
        }
    }
}
