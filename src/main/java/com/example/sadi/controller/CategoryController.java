package com.example.sadi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sadi.model.Category;
import com.example.sadi.model.Product;
import com.example.sadi.service.CategoryService;
import com.example.sadi.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping
    public String getAllCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "category/list";
    }

    @GetMapping("/{id}")
    public String showCategoryProducts(@PathVariable Long id, Model model) {
        try {
            log.debug("Поиск категории с ID: {}", id);
            
            Category category = categoryService.getCategoryById(id);
            if (category == null) {
                log.error("Категория не найдена: {}", id);
                return "redirect:/";
            }

            List<Product> products = productService.getProductsByCategory(category);
            log.debug("Найдено {} продуктов для категории {}", 
                     products != null ? products.size() : 0, 
                     category.getName());
            
            model.addAttribute("category", category);
            model.addAttribute("products", products);
            return "category/products";
            
        } catch (Exception e) {
            log.error("Ошибка при показе продуктов категории", e);
            return "redirect:/";
        }
    }
}
