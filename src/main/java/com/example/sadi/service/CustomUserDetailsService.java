package com.example.sadi.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.sadi.model.User;  // Твой пользовательский класс
import com.example.sadi.repository.UserRepository;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);  // Получаем пользователя из базы данных

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Используем SimpleGrantedAuthority для указания ролей
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER");

        // Создаем объект User с ролями
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            Collections.singletonList(authority)  // Передаем список с одной ролью
        );
    }
}
