package com.booleanuk.api.repository;

import com.booleanuk.api.model.BorrowedGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowedGameRepository extends JpaRepository<BorrowedGame, Integer> {
}
