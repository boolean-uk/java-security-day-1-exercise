package com.booleanuk.api.Repositories;

import com.booleanuk.api.Models.VideoGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoGameRepository extends JpaRepository<VideoGame, Integer> {
}
