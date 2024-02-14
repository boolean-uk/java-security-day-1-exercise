package com.booleanuk.api.controller;

import com.booleanuk.api.exceptions.CustomDataNotFoundException;
import com.booleanuk.api.exceptions.CustomParamaterConstraintException;


import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.response.SuccessResponse;
import com.booleanuk.api.util.DateCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Response<?>> createLibrary(@RequestBody User library) {

        User library1 = new User(library.getName(), DateCreater.getCurrentDate(), DateCreater.getCurrentDate());
        checkValidInput(library1);
        this.userRepository.save(library1);

        return new ResponseEntity<>(new SuccessResponse(library1), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<User>> getLibraries() {
        return new ResponseEntity<>(this.userRepository.findAll(), HttpStatus.OK);
    }



    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getSpecificLibrary(@PathVariable(name = "id") int id) {
        User library = this.getALibrary(id);

        return new ResponseEntity<>(new SuccessResponse(library), HttpStatus.OK);

    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateLibrary(@PathVariable (name = "id") int id, @RequestBody User library) {
        Library library1 = getALibrary(id);
        library1.setUpdatedAt(DateCreater.getCurrentDate());
        library1.setName(library.getName());
        if(library1.getGames() == null) {
            library1.setGames(new ArrayList<>());
        } else {
            library1.setGames(library.getGames());
        }

        this.checkValidInput(library1);

        return new ResponseEntity<>(new SuccessResponse(library1), HttpStatus.CREATED);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteLibrary(@PathVariable (name = "id") int id) {
        User user = this.getALibrary(id);

        this.userRepository.delete(this.getALibrary(id));

        return new ResponseEntity<>(new SuccessResponse(user), HttpStatus.OK);

    }

    private User getALibrary(int id) {
        return this.userRepository.findById(id).orElseThrow(() -> new CustomDataNotFoundException("No library with that ID found"));
    }


    private void checkValidInput(User library) {
        if(library.getCreatedAt() == null || library.getName() == null || library.getUpdatedAt() == null) {
            throw new CustomParamaterConstraintException("Bad request");
        }
    }
}
