package com.booleanuk.api.videoGames;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoGameRepository extends JpaRepository<VideoGame,Integer> {
}
