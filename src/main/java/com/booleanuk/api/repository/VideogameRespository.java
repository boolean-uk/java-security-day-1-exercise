package com.booleanuk.api.repository;

import com.booleanuk.api.model.Videogame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideogameRespository extends JpaRepository<Videogame, Integer> {
}
