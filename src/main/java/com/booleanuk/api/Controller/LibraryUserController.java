package com.booleanuk.api.Controller;

import com.booleanuk.api.Model.LibraryUser;
import com.booleanuk.api.Repository.LibraryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/libraryUsers")
public class LibraryUserController {

    @Autowired
    private LibraryUserRepository libraryUserRepository;

    @PostMapping
    public ResponseEntity<LibraryUser> createUser(@RequestBody LibraryUser libraryUser) {
        LibraryUser createdUser = libraryUserRepository.save(libraryUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<LibraryUser>> getAllUsers() {
        List<LibraryUser> users = libraryUserRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryUser> getUserById(@PathVariable Long id) {
        Optional<LibraryUser> user = libraryUserRepository.findById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping("/{id}")
    public ResponseEntity<LibraryUser> updateUser(@PathVariable Long id, @RequestBody LibraryUser libraryUserDetails) {
        Optional<LibraryUser> existingUser = libraryUserRepository.findById(id);
        if (existingUser.isPresent()) {
            LibraryUser user = existingUser.get();
            user.setUsername(libraryUserDetails.getUsername());
            user.setPassword(libraryUserDetails.getPassword());
            user.setRole(libraryUserDetails.getRole());
            libraryUserRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<LibraryUser> user = libraryUserRepository.findById(id);
        if (user.isPresent()) {
            libraryUserRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
