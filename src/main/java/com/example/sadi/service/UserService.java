package com.example.sadi.service;

import org.springframework.stereotype.Service;

import com.example.sadi.model.User;
import com.example.sadi.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User authenticate(String username, String password) {
        User user = findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
