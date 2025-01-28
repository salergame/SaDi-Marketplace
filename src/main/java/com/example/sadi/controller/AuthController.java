package com.example.sadi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String contactPage() {
        return "login";  // Здесь "contact" - это имя вашего HTML-шаблона.
    }
}
