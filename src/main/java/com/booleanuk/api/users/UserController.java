package com.booleanuk.api.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
  private final UserRepository repository;

  public UserController(UserRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public ResponseEntity<List<User>> get() {
    return ResponseEntity.ok(this.repository.findAll());
  }

  @GetMapping("{id}")
  public ResponseEntity<User> get(@PathVariable int id) throws ResponseStatusException {
    return ResponseEntity.ok(this.repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
  }

  @PostMapping
  public ResponseEntity<User> post(@RequestBody User user) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.repository.save(user));
  }

  @PutMapping("{id}")
  public ResponseEntity<User> put(@PathVariable int id, @RequestBody User user) {
    return ResponseEntity.status(HttpStatus.CREATED).body(
        this.repository.findById(id)
            .map(existing -> {
              existing.setName(user.getName());
              existing.setAge(user.getAge());
              existing.setBorrowedGames(user.getBorrowedGames());
              return existing;
            })
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
    );
  }

  @DeleteMapping("{id}")
  public ResponseEntity<User> delete(@PathVariable int id) {
    return ResponseEntity.ok(this.repository.findById(id)
        .map(existing -> {
          this.repository.deleteById(id);
          return existing;
        })
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
  }
}