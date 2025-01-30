package com.example.sadi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sadi.model.CartItem;
import com.example.sadi.model.Product;
import com.example.sadi.model.User;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    CartItem findByUserAndProduct(User user, Product product);
    CartItem findByUserAndProductId(User user, Long productId);
    void deleteByUserAndProductId(User user, Long productId);
} 