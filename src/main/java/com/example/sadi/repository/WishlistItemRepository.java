package com.example.sadi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sadi.model.Product;
import com.example.sadi.model.User;
import com.example.sadi.model.WishlistItem;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByUser(User user);
    WishlistItem findByUserAndProduct(User user, Product product);
    void deleteByUserAndProduct(User user, Product product);
    void deleteByUserAndProductId(User user, Long productId);
} 