package com.booleanuk.api.Repository;


import com.booleanuk.api.Model.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryUserRepository extends JpaRepository<LibraryUser, Long> {
    LibraryUser findByUsername(String username);
}
