package com.booleanuk.library.repository;

import com.booleanuk.library.model.GameLoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameLoanRepository extends JpaRepository<GameLoan, Integer>{
}
