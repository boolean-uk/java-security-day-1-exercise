package com.booleanuk.api.controllers;

import com.booleanuk.api.repositories.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("video-games")
public class VideoGameController {
    @Autowired
    private VideoGameRepository videoGameRepository;

}
