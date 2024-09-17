package com.booleanuk.api.games;

import com.booleanuk.api.users.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("games")
public class GameController {
  private final GameRepository repository;

  public GameController(GameRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public ResponseEntity<List<Game>> get() {
    return ResponseEntity.ok(this.repository.findAll());
  }

  @GetMapping("{id}")
  public ResponseEntity<Game> get(@PathVariable int id) throws ResponseStatusException {
    return ResponseEntity.ok(this.repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
  }

  @PostMapping
  public ResponseEntity<Game> post(@RequestBody Game game) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.repository.save(game));
  }

  @PostMapping("{id}/borrow")
  public ResponseEntity<Game> borrow(@PathVariable int id, @RequestBody User user) throws ResponseStatusException {
    var maybeBorrowed = this.repository.findById(id);
    if (maybeBorrowed.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    var borrowed = maybeBorrowed.get();
    if (borrowed.getBorrowedTo() != null)
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    else
      borrowed.setBorrowedTo(user);

    return ResponseEntity.status(HttpStatus.CREATED).body(this.repository.save(borrowed));
  }

  @PostMapping("{id}/deliver")
  public ResponseEntity<Game> deliver(@PathVariable int id, @RequestBody User user) throws ResponseStatusException {
    var maybeBorrowed = this.repository.findById(id);
    if (maybeBorrowed.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    var borrowed = maybeBorrowed.get();
    if (borrowed.getBorrowedTo() == null)
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    else
      borrowed.setBorrowedTo(null);

    return ResponseEntity.status(HttpStatus.CREATED).body(this.repository.save(borrowed));
  }
}