package com.booleanuk.api.repository;

import com.booleanuk.api.model.BorrowGames;
import com.booleanuk.api.model.User;
import com.booleanuk.api.model.VideoGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowGamesRepository extends JpaRepository<BorrowGames, Integer> {
    List<BorrowGames> findByUser(User user);
    Optional<BorrowGames> findByUserAndVideoGame(User user, VideoGame videoGame);
    List<BorrowGames> findByVideoGame(VideoGame videoGame);

}
