package com.booleanuk.api.controller;

import com.booleanuk.api.model.Rental;
import com.booleanuk.api.model.User;
import com.booleanuk.api.model.Videogame;
import com.booleanuk.api.repository.RentalRepository;
import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.api.repository.VideogameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("rentals")
public class RentalController {

    @Autowired
    private final RentalRepository repository;

    @Autowired
    private VideogameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    public RentalController(RentalRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getOne(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.repository.findById(id).orElseThrow());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Rental> createRental(@RequestBody Rental rental) {
        Videogame game = this.gameRepository.findById(rental.getGame().getId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author found..")
        );
        rental.setGame(game);
        User user = this.userRepository.findById(rental.getUser().getId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author found..")
        );
        rental.setUser(user);
        return new ResponseEntity<>(this.repository.save(rental), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Rental> updateAuthor(@PathVariable("id") Integer id,
                                               @RequestBody Rental rental) {
        Rental rentalToUpdate = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existing rental with that ID found")
        );
        rentalToUpdate.setIsActive(rental.getIsActive());
        rentalToUpdate.setLength_days(rental.getLength_days());
        return new ResponseEntity<>(this.repository.save(rentalToUpdate),
                HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Rental> deleteAuthor(@PathVariable("id") Integer id) {
        Rental rentalToDelete = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No rental with that ID were found")
        );
        this.repository.delete(rentalToDelete);
        return ResponseEntity.ok(rentalToDelete);
    }


}
