package com.booleanuk.api.Repository;

import com.booleanuk.api.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
