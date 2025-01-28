package com.example.sadi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sadi.model.Product;
import com.example.sadi.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }
}