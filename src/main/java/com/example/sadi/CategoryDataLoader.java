package com.example.sadi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.sadi.model.Category;
import com.example.sadi.repository.CategoryRepository;

@Component
public class CategoryDataLoader implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        // Добавляем категории, если их еще нет в базе данных
        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category(null, "Woman’s Fashion"));
            categoryRepository.save(new Category(null, "Men’s Fashion"));
            categoryRepository.save(new Category(null, "Electronics"));
            categoryRepository.save(new Category(null, "Home & Lifestyle"));
            categoryRepository.save(new Category(null, "Medicine"));
            categoryRepository.save(new Category(null, "Sports & Outdoor"));
            categoryRepository.save(new Category(null, "Baby’s & Toys"));
            categoryRepository.save(new Category(null, "Groceries & Pets"));
            categoryRepository.save(new Category(null, "Health & Beauty"));
        }
    }
}
