package com.example.sadi.controller;

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

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String loginPage() {
        return "login";  // Страница логина
    }

    @PostMapping("/signup")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session, Model model) {

        // Убираем ручную аутентификацию через сервис. Это будет делать Spring Security.
        return "redirect:/home";  // После успешной авторизации Spring Security перенаправит сюда
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        return "dashboard";  // Это будет отображать информацию о пользователе на dashboard
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
    