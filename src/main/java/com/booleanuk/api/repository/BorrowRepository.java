package com.booleanuk.api.repository;

import com.booleanuk.api.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow, Integer> {
    List<Borrow> findByUserId(int userID);
}
