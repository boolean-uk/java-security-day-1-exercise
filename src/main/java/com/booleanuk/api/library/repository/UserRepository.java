package com.booleanuk.api.library.repository;

import com.booleanuk.api.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
