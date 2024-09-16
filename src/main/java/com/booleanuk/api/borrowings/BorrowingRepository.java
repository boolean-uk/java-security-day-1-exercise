package com.booleanuk.api.borrowings;

import com.booleanuk.api.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowingRepository extends JpaRepository<Borrowing,Integer> {

    List<Borrowing> findByUser(User user);
}
