package com.booleanuk.api.controller;

import com.booleanuk.api.exceptions.CustomDataNotFoundException;
import com.booleanuk.api.exceptions.CustomParamaterConstraintException;
import com.booleanuk.api.model.Library;
import com.booleanuk.api.repository.LibraryRepository;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.response.SuccessResponse;
import com.booleanuk.api.util.DateCreater;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("libraries")
public class LibraryController {

    @Autowired
    private LibraryRepository libraryRepository;

    @PostMapping
    public ResponseEntity<Response<?>> createLibrary(@RequestBody Library library) {

        Library library1 = new Library(library.getName(), DateCreater.getCurrentDate(), DateCreater.getCurrentDate());
        checkValidInput(library1);
        this.libraryRepository.save(library1);

        return new ResponseEntity<>(new SuccessResponse(library1), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<Library>> getLibraries() {
        return new ResponseEntity<>(this.libraryRepository.findAll(), HttpStatus.OK);
    }



    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getSpecificLibrary(@PathVariable (name = "id") int id) {
        Library library = this.getALibrary(id);

        return new ResponseEntity<>(new SuccessResponse(library), HttpStatus.OK);

    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateLibrary(@PathVariable (name = "id") int id, @RequestBody Library library) {
        Library library1 = getALibrary(id);
        library1.setUpdatedAt(DateCreater.getCurrentDate());
        library1.setName(library.getName());
        library1.setCreatedAt(library.getCreatedAt());
        library1.setGames(library.getGames());

        this.checkValidInput(library1);

        return new ResponseEntity<>(new SuccessResponse(library1), HttpStatus.CREATED);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteLibrary(@PathVariable (name = "id") int id) {
        Library library = this.getALibrary(id);

        this.libraryRepository.delete(library);

        return new ResponseEntity<>(new SuccessResponse(library), HttpStatus.OK);

    }

    private Library getALibrary(int id) {
        return this.libraryRepository.findById(id).orElseThrow(() -> new CustomDataNotFoundException("No library with that ID found"));
    }


    private void checkValidInput(Library library) {
        if(library.getCreatedAt() == null || library.getName() == null || library.getUpdatedAt() == null) {
            throw new CustomParamaterConstraintException("Bad request");
        }
    }
}
