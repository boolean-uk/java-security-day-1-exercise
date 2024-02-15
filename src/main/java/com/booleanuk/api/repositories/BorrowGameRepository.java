package com.booleanuk.api.repositories;

import com.booleanuk.api.models.BorrowGame;
import com.booleanuk.api.models.Customer;
import com.booleanuk.api.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowGameRepository extends JpaRepository<BorrowGame, Integer> {
    Optional<BorrowGame> findByCustomerAndGame(Customer customer, Game game);
    Optional<List<BorrowGame>> findAllByCustomer(Customer customer);
    Optional<List<BorrowGame>> findAllByGame(Game game);
    boolean existsByGameAndIsActiveTrue(Game game);
}
