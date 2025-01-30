package com.example.sadi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sadi.model.Category;
import com.example.sadi.model.Product;
import com.example.sadi.repository.CategoryRepository;
import com.example.sadi.repository.ProductRepository;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product save(Product product) {
        if (product.getCategory() == null) {
            throw new RuntimeException("Category is required");
        }
        // Для отладки
        System.out.println("Saving product: " + product.getName());
        System.out.println("Category: " + product.getCategory().getName());
        System.out.println("Image URL: " + product.getImageUrl());
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(Category category) {
        try {
            List<Product> products = productRepository.findByCategory(category);
            System.out.println("Found " + products.size() + " products for category " + category.getName());
            return products;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Product getProductById(Long id) {
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                System.out.println("Found product with id " + id + ": " + product.get().getName());
                return product.get();
            }
            System.out.println("Product with id " + id + " not found");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}