package com.booleanuk.api.Repositories;

import com.booleanuk.api.Models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
}
