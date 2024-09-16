package com.booleanuk.api.repository;

import com.booleanuk.api.model.User;
import org.springframework.http.ResponseEntity;

public interface BorrowedGameRepository { ResponseEntity<User> borrowGame(int userId, int gameId);
}
