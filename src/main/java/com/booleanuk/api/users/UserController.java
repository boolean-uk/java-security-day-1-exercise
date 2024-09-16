package com.booleanuk.api.users;


import com.booleanuk.api.Exceptions.BadRequestException;
import com.booleanuk.api.Exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<User> customers = userRepository.findAll();;
        return new ResponseEntity<>(customers, HttpStatus.OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOne(@PathVariable int id) {
        User user = this.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) {

        try{
            User userToCreate = this.userRepository.save(user);
            return new ResponseEntity<>(userToCreate, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }

    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable int id) {
        User userToUpdate = findById(id);
        try{
            userToUpdate.setName(user.getName());
            userToUpdate.setPhoneNumber(user.getPhoneNumber());

            this.userRepository.save(userToUpdate);

            return new ResponseEntity<>(userToUpdate, HttpStatus.CREATED);
        }
        catch (Exception e){
            throw new BadRequestException("bad request");
        }

    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        User user = findById(id);
        this.userRepository.delete(user);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    private User findById(int id) {
        return this.userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("user not found with ID " + id)
        );

    }

}
