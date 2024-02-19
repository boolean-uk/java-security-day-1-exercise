package com.booleanuk.api.repository;

import com.booleanuk.api.model.VideoGame;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VideoGameRepository extends JpaRepository<VideoGame, Integer> {
}
