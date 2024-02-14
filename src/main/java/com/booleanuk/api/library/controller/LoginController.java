package com.booleanuk.api.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class LoginController {
    @GetMapping("/login")
    String login() {
        return "login";
    }
}