package com.booleanuk.api.library.repository;

import com.booleanuk.api.library.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
}
