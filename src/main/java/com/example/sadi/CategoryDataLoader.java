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
            categoryRepository.save(new Category(null, "Woman's Fashion", "Women's clothing and accessories", null));
            categoryRepository.save(new Category(null, "Men's Fashion", "Men's clothing and accessories", null));
            categoryRepository.save(new Category(null, "Electronics", "Electronic devices and gadgets", null));
            categoryRepository.save(new Category(null, "Home & Lifestyle", "Home and lifestyle products", null));
            categoryRepository.save(new Category(null, "Medicine", "Medical supplies and equipment", null));
            categoryRepository.save(new Category(null, "Sports & Outdoor", "Sports and outdoor equipment", null));
            categoryRepository.save(new Category(null, "Baby's & Toys", "Baby products and toys", null));
            categoryRepository.save(new Category(null, "Groceries & Pets", "Groceries and pet supplies", null));
            categoryRepository.save(new Category(null, "Health & Beauty", "Health and beauty products", null));
        }
    }
}
