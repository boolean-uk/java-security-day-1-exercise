package com.booleanuk.api.controller;

import com.booleanuk.api.model.Loan;
import com.booleanuk.api.model.User;
import com.booleanuk.api.model.VideoGame;
import com.booleanuk.api.repository.LoanRepository;
import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.api.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

	@Autowired
	private LoanRepository loanRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private VideoGameRepository videoGameRepository;


	@GetMapping
	public ResponseEntity<List<Loan>> getAllLoans() {
		List<Loan> loans = loanRepository.findAll();
		return new ResponseEntity<>(loans, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Loan> getLoanById(@PathVariable int id) {
		Loan loan = loanRepository.findById(id).orElse(null);
		if (loan != null) {
			return new ResponseEntity<>(loan, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<Loan> addLoan(@RequestBody Loan loan) {
		User user = userRepository.findById(loan.getUser().getId()).orElse(null);
		VideoGame videoGame = videoGameRepository.findById(loan.getVideo_game().getId()).orElse(null);

		Loan newLoan = new Loan(user,videoGame,loan.getDate(),loan.isReturned());
		loanRepository.save(newLoan);
		return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Loan> updateLoan(@PathVariable int id, @RequestBody Loan loan) {
		User user = userRepository.findById(id).orElse(null);
		VideoGame videoGame = videoGameRepository.findById(id).orElse(null);

		Loan updatedLoan = loanRepository.findById(id).orElse(null);
		if (updatedLoan != null && user != null && videoGame != null) {
			if (loan.getUser() != null) {
				updatedLoan.setUser(user);
			}
			if (loan.getVideo_game() != null) {
				updatedLoan.setVideo_game(videoGame);
			}
			if (loan.getDate() != null) {
				updatedLoan.setDate(loan.getDate());
			}
			updatedLoan.setReturned(loan.isReturned());

			loanRepository.save(updatedLoan);
			return new ResponseEntity<>(updatedLoan, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Loan> deleteLoan(@PathVariable int id) {
		Loan loan = loanRepository.findById(id).orElse(null);
		if (loan != null) {
			loanRepository.delete(loan);
			return new ResponseEntity<>(loan, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}

