package com.example.sadi.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sadi.model.User;
import com.example.sadi.service.UserService;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
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
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("username", user.getUsername());
        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
