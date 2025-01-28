package com.example.sadi;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.sadi.model.Category;
import com.example.sadi.repository.CategoryRepository;

@Component
public class CategoryDataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public CategoryDataLoader(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Проверяем, есть ли категории в базе данных, чтобы избежать повторного добавления
        if (categoryRepository.count() == 0) {
            List<Category> categories = Arrays.asList(
                    new Category(null, "Woman’s Fashion"),
                    new Category(null, "Men’s Fashion"),
                    new Category(null, "Electronics"),
                    new Category(null, "Home & Lifestyle"),
                    new Category(null, "Medicine"),
                    new Category(null, "Sports & Outdoor"),
                    new Category(null, "Baby’s & Toys"),
                    new Category(null, "Groceries & Pets"),
                    new Category(null, "Health & Beauty")
            );

            categoryRepository.saveAll(categories); // Сохраняем все категории в базе данных
        }
    }
}
