package com.booleanuk.api.repository;

import com.booleanuk.api.dto.BorrowedGameDto;
import com.booleanuk.api.model.BorrowedGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BorrowedGameRepository extends JpaRepository<BorrowedGame, Integer> {
    @Query("SELECT new com.booleanuk.api.dto.BorrowedGameDto(b.id, g.title, u.username, b.borrowDate, b.returnDate) " +
            "FROM BorrowedGame b " +
            "JOIN b.game g " +
            "JOIN b.user u " +
            "WHERE g.id = :gameId")
    List<BorrowedGameDto> findBorrowedGamesByGameId(int gameId);
    @Query("SELECT new com.booleanuk.api.dto.BorrowedGameDto(b.id, g.title, u.username, b.borrowDate, b.returnDate) " +
            "FROM BorrowedGame b " +
            "JOIN b.game g " +
            "JOIN b.user u " +
            "WHERE g.id = :userId")
    List<BorrowedGameDto> findBorrowedGamesByUserId(int userId);
}
