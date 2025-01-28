package com.example.sadi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.sadi.model.Category;
import com.example.sadi.service.CategoryService;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Этот метод загружает все категории на главную страницу
    @GetMapping("/")
    public String indexPage(Model model) {
        // Получаем все категории
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "index"; // Возвращаем название шаблона index.html
    }

    // Этот метод загружает данные конкретной категории по ID
    @GetMapping("/categories/{id}")
    public String getCategory(@PathVariable Long id, Model model) {
        // Получаем категорию по ID
        Category category = categoryService.getCategoryById(id);
        
        // Если категория не найдена, можно отобразить ошибку
        if (category == null) {
            model.addAttribute("error", "Category not found");
            return "error";  // Страница ошибки
        }

        model.addAttribute("category", category);
        return "category";  // Страница, которая будет отображать детали категории
    }
}
