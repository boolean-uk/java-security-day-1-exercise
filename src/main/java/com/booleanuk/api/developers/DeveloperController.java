package com.booleanuk.api.developers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("developers")
public class DeveloperController {
  private final DeveloperRepository repository;

  public DeveloperController(DeveloperRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public ResponseEntity<List<Developer>> get() {
    return ResponseEntity.ok(this.repository.findAll());
  }

  @GetMapping("{id}")
  public ResponseEntity<Developer> get(@PathVariable int id) throws ResponseStatusException {
    return ResponseEntity.ok(this.repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
  }

  @PostMapping
  public ResponseEntity<Developer> post(@RequestBody Developer developer) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.repository.save(developer));
  }
}