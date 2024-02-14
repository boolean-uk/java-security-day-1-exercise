package com.booleanuk.api.library.repository;

import com.booleanuk.api.library.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {
}
