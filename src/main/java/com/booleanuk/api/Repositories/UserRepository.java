package com.booleanuk.api.Repositories;

import com.booleanuk.api.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
