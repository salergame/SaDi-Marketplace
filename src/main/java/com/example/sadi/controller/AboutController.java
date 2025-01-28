package com.example.sadi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {
    @GetMapping("/about")
    public String contactPage() {
        return "about";  // Здесь "contact" - это имя вашего HTML-шаблона.
    }
}
