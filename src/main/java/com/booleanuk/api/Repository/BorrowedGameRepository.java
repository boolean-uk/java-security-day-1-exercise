package com.booleanuk.api.Repository;

import com.booleanuk.api.Model.BorrowedGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowedGameRepository extends JpaRepository<BorrowedGame, Long> {

}
