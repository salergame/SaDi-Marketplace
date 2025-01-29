package com.example.sadi.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.sadi.model.User;
import com.example.sadi.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(); // Инициализация BCryptPasswordEncoder
    }

    public User authenticate(String username, String password) {
        User user = findByUsername(username).orElse(null);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user; // Пароль верный
        }
        return null; // Неверный пароль
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
