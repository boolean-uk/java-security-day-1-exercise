package com.booleanuk.api.Controllers;

import com.booleanuk.api.Models.User;
import com.booleanuk.api.Repositories.UserRepository;
import com.booleanuk.api.Responses.ErrorResponse;
import com.booleanuk.api.Responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserRepository repo;

    @GetMapping
    public ResponseEntity<Response<List<User>>> getAll(){
        Response<List<User>> usersResponse = new Response<>();
        usersResponse.set(repo.findAll());

        return ResponseEntity.ok(usersResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getOne(@PathVariable int id){
        User user = repo.findById(id).orElse(null);

        if (user == null){
            ErrorResponse notFound = new ErrorResponse();
            notFound.set("not found");

            return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
        }

        Response<User> userResponse = new Response<>();
        userResponse.set(user);

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> add(@RequestBody User user){
        if (user.getName() == null || user.getPhone() == null){
            ErrorResponse badRequest = new ErrorResponse();
            badRequest.set("bad request");

            return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
        }

        repo.save(user);
        Response<User> userResponse = new Response<>();
        userResponse.set(user);

        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody User user){
        if (user.getName() == null || user.getPhone() == null){
            ErrorResponse badRequest = new ErrorResponse();
            badRequest.set("bad request");

            return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
        }

        User toUpdate = repo.findById(id).orElse(null);

        if (toUpdate == null){
            ErrorResponse notFound = new ErrorResponse();
            notFound.set("not found");

            return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
        }

        Optional.ofNullable(user.getName()).ifPresent(name -> toUpdate.setName(name));
        Optional.ofNullable(user.getPhone()).ifPresent(phone -> toUpdate.setPhone(phone));
        repo.save(toUpdate);

        Response<User> userResponse = new Response<>();
        userResponse.set(toUpdate);

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id){
        User toDelete = repo.findById(id).orElse(null);

        if (toDelete == null){
            ErrorResponse notFound = new ErrorResponse();
            notFound.set("not found");

            return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
        }

        repo.delete(toDelete);
        Response<User> userResponse = new Response<>();
        userResponse.set(toDelete);

        return ResponseEntity.ok(userResponse);
    }

}
