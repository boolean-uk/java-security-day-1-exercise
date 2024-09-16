package com.booleanuk.api.controller;

import com.booleanuk.api.model.Game;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class GameController extends GenericController<Game, Integer> {
}
