package com.example.sadi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sadi.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {}