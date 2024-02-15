package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Lend;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("lends")
public class LendController extends ControllerTemplate<Lend> {
}
