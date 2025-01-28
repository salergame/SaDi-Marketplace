package com.example.sadi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sadi.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {}