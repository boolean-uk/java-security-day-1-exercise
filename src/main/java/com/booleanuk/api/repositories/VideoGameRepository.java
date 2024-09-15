package com.booleanuk.api.repositories;

import com.booleanuk.api.models.VideoGame;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoGameRepository extends JpaRepository<VideoGame, Integer> {

}
