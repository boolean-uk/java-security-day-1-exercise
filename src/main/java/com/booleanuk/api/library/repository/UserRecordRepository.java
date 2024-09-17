package com.booleanuk.api.library.repository;

import com.booleanuk.api.library.model.User;
import com.booleanuk.api.library.model.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRecordRepository extends JpaRepository<UserRecord, Integer> {

    public List<UserRecord> findAllById(int id);


}
