package com.booleanuk.api.videogames;

import com.booleanuk.api.videogames.VideoGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoGameRepository extends JpaRepository<VideoGame, Integer> {
}
