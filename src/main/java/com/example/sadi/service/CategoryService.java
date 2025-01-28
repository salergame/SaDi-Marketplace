package com.example.sadi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sadi.model.Category;
import com.example.sadi.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Метод для получения всех категорий
    public List<Category> getAllCategories() {
        return categoryRepository.findAll(); // Возвращаем все категории из базы данных
    }

    // Метод для получения категории по ID
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null); // Если категория не найдена, вернется null
    }

    // Метод для добавления новой категории
    public Category addCategory(Category category) {
        return categoryRepository.save(category); // Сохраняем категорию в базе данных
    }

    // Метод для удаления категории
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id); // Удаляем категорию по ID
    }

    // Метод для обновления категории
    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            category.setName(categoryDetails.getName()); // Обновляем имя категории
            return categoryRepository.save(category); // Сохраняем обновленную категорию
        }
        return null;
    }
}
