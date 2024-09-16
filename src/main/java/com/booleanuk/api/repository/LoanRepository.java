package com.booleanuk.api.repository;

import com.booleanuk.api.model.Game;
import com.booleanuk.api.model.Loan;
import com.booleanuk.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    List<Loan> findByUserAndGame(User user, Game game);

    List<Loan> findByReturnedAndGame(boolean returned, Game game);
}
