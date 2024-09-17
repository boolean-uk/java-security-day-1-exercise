package com.booleanuk.api.repository;

import com.booleanuk.api.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer> {
    List<Game> findAllByTitle(String title);
}
