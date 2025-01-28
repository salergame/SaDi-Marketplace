package com.example.sadi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sadi.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}