package com.example.sadi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sadi.model.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {}