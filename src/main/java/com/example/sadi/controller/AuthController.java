package com.example.sadi.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

    User user = userService.findByUsername(username);

    if (user != null && passwordEncoder.matches(password, user.getPassword())) {
        session.setAttribute("user", user);
        return "redirect:/"; // Перенаправление на главную страницу
    } else {
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }
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
