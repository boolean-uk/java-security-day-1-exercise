package com.booleanuk.api.Repository;

import com.booleanuk.api.Model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {

}
