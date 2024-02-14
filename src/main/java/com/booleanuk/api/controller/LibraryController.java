package com.booleanuk.api.controller;

import com.booleanuk.api.repository.LibraryRepository;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("libraries")
public class LibraryController {

    @Autowired
    private LibraryRepository libraryRepository;

}
