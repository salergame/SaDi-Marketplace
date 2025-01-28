package com.example.sadi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sadi.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {}