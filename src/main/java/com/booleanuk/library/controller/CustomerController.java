package com.booleanuk.library.controller;


import com.booleanuk.library.model.Customer;
import com.booleanuk.library.repository.CustomerRepository;
import com.booleanuk.library.response.CustomResponse;
import com.booleanuk.library.response.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<CustomResponse> getAllCustomers() {
        CustomResponse customResponse = new CustomResponse("success", customerRepository.findAll());
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomResponse> getCustomerById(@PathVariable("id") Integer id) {
        if(!customerRepository.existsById(id)) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("not found")), HttpStatus.NOT_FOUND);
        }

        CustomResponse customResponse = new CustomResponse("success", customerRepository.findById(id).get());
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomResponse> createCustomer(@RequestBody Customer customer) {
        if (customer.getName() == null || customer.getName().isEmpty()) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("Name is required")), HttpStatus.BAD_REQUEST);
        } else if (customer.getEmail() == null || customer.getEmail().isEmpty()) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("Email is required")), HttpStatus.BAD_REQUEST);
        } else if (customer.getPhone() == null || customer.getPhone().isEmpty()) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("Phone number is required")), HttpStatus.BAD_REQUEST);
        }

        Customer newCustomer = customerRepository.save(customer);

        CustomResponse customResponse = new CustomResponse("success", newCustomer);
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<CustomResponse> updateCustomer(@PathVariable("id") Integer id, @RequestBody Customer customer) {
        if(!customerRepository.existsById(id)) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("not found")), HttpStatus.NOT_FOUND);
        }

        if (customer.getName() == null || customer.getName().isEmpty()) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("Name is required")), HttpStatus.BAD_REQUEST);
        } else if (customer.getEmail() == null || customer.getEmail().isEmpty()) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("Email is required")), HttpStatus.BAD_REQUEST);
        } else if (customer.getPhone() == null || customer.getPhone().isEmpty()) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("Phone number is required")), HttpStatus.BAD_REQUEST);
        }

        Customer customerToUpdate = customerRepository
                .findById(id).get();
        customerToUpdate.setName(customer.getName() != null ? customer.getName() : customerToUpdate.getName());
        customerToUpdate.setEmail(customer.getEmail() != null ? customer.getEmail() : customerToUpdate.getEmail());
        customerToUpdate.setPhone(customer.getPhone() != null ? customer.getPhone() : customerToUpdate.getPhone());
        customerRepository.save(customerToUpdate);

        CustomResponse customResponse = new CustomResponse("success", customerToUpdate);
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CustomResponse> deleteCustomer(@PathVariable("id") Integer id) {
        if(!customerRepository.existsById(id)) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("not found")), HttpStatus.NOT_FOUND);
        }

        Customer customerToDelete = customerRepository
                .findById(id).get();
        customerRepository.delete(customerToDelete);

        CustomResponse customResponse = new CustomResponse("success", customerToDelete);
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }
}

