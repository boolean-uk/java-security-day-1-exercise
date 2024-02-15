package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Game;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("games")
public class GameController extends ControllerTemplate<Game> {
}
