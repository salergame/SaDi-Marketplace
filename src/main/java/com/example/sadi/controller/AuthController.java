package com.example.sadi.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpSession;

import com.example.sadi.model.User;
import com.example.sadi.service.UserService;
import com.example.sadi.model.User;
import com.example.sadi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage(Principal principal) {
        if (principal != null) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(Principal principal) {
        if (principal != null) {
            return "redirect:/";
        }
        return "register";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {
        try {
            if (username == null || username.trim().isEmpty() || 
                password == null || password.trim().isEmpty()) {
                model.addAttribute("error", "Имя пользователя и пароль обязательны");
                return "register";
            }

            if (userService.findByUsername(username) != null) {
                model.addAttribute("error", "Пользователь с таким именем уже существует");
                return "register";
            }

            User user = new User();
            user.setUsername(username.trim());
            user.setPassword(password);
            user.setAdmin(false);
            
            try {
                userService.save(user);
                return "redirect:/login?registered=true";
            } catch (Exception e) {
                model.addAttribute("error", "Ошибка при сохранении пользователя: " + e.getMessage());
                return "register";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Произошла ошибка при регистрации: " + e.getMessage());
            return "register";
        }
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "profile";
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            return "redirect:/register?error=exists";
        }
        User newUser = new User(username, passwordEncoder.encode(password), false);
        userRepository.save(newUser);
        return "redirect:/login?registered";
    }
}
