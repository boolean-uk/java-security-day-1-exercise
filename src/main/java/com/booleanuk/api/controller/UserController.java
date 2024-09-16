package com.booleanuk.api.controller;


import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;


	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userRepository.findAll();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable int id) {
		User user = userRepository.findById(id).orElse(null);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<User> addUser(@RequestBody User user) {
		User newuser = userRepository.save(user);
		return new ResponseEntity<>(newuser, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
		User updatedUser = userRepository.findById(id).orElse(null);
		if (updatedUser != null) {
			if (user.getFirst_name() != null) {
				updatedUser.setFirst_name(user.getFirst_name());
			}
			if (user.getLast_name() != null) {
				updatedUser.setLast_name(user.getLast_name());
			}
			if (user.getEmail() != null) {
				updatedUser.setEmail(user.getEmail());
			}
			if (user.getPhone() != null) {
				updatedUser.setPhone(user.getPhone());
			}

			userRepository.save(updatedUser);
			return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable int id) {
		User user = userRepository.findById(id).orElse(null);
		if (user != null) {
			userRepository.delete(user);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}

