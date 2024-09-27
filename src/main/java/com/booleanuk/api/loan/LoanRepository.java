package com.booleanuk.api.loan;

import com.booleanuk.api.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    List<Loan> findByUser(User user);
}
