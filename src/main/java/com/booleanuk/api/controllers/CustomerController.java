package com.booleanuk.api.controllers;

import com.booleanuk.api.models.BorrowGame;
import com.booleanuk.api.models.Customer;
import com.booleanuk.api.repositories.BorrowGameRepository;
import com.booleanuk.api.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("customers")
@AllArgsConstructor
public class CustomerController {
    private CustomerRepository customerRepository;
    private BorrowGameRepository borrowGameRepository;

    @GetMapping
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer add(@RequestBody Customer customer) {
        if (customer.haveNullFields()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return customerRepository.save(customer);
    }

    @GetMapping("{id}")
    public Customer getById(@PathVariable int id) {
        if (customerRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return customerRepository.findById(id).get();
    }

    @GetMapping("{id}/games")
    public List<BorrowGame> getBorrowedGames(@PathVariable int id) {
        if (customerRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Customer customer = customerRepository.findById(id).get();
        return borrowGameRepository.findAllByCustomer(customer).get();
    }

    @PutMapping("{id}")
    public Customer update(@PathVariable int id, @RequestBody Customer customer) {
        if (customerRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Customer customerToUpdate = customerRepository.findById(id).get();
        customerToUpdate.setName(customer.getName() != null ? customer.getName() : customerToUpdate.getName());
        customerToUpdate.setEmail(customer.getEmail() != null ? customer.getEmail() : customerToUpdate.getEmail());
        customerToUpdate.setPhone(customer.getPhone() != null ? customer.getPhone() : customerToUpdate.getPhone());

        return customerRepository.save(customerToUpdate);
    }

    @DeleteMapping("{id}")
    public Customer delete(@PathVariable int id) {
        if (customerRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Customer customerToDelete = customerRepository.findById(id).get();
        customerRepository.delete(customerToDelete);
        return customerToDelete;
    }
}
