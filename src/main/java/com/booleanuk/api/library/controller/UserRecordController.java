package com.booleanuk.api.library.controller;

import com.booleanuk.api.library.model.User;
import com.booleanuk.api.library.model.UserRecord;
import com.booleanuk.api.library.repository.UserRecordRepository;
import com.booleanuk.api.library.repository.UserRepository;
import com.booleanuk.api.library.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("records")
public class UserRecordController {

    @Autowired
    private UserRecordRepository userRecordRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/{id}")
    public ResponseEntity<User>getUserWithAllPreviousGames(@PathVariable int id){
        User user = this.userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        for(UserRecord ur : this.userRecordRepository.findAllById(id)){
            if(!user.getGames().contains(ur.getGame())){
                user.getGames().add(ur.getGame());
            }
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
