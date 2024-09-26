package com.booleanuk.api.repository;

import com.booleanuk.api.model.Game;
import com.booleanuk.api.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, Integer> {
}
