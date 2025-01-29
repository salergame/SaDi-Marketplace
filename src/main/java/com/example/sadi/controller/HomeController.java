package com.example.sadi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home") // Изменено с "/" на "/home"
    public String home() {
        return "index";
    }
}

