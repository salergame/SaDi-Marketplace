package com.example.sadi.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.sadi.model.Category;
import com.example.sadi.model.Product;
import com.example.sadi.service.CategoryService;
import com.example.sadi.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final CategoryService categoryService;
    private final ProductService productService;

    public AdminController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping
    public String adminPanel(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/panel";
    }

    @PostMapping("/categories/add")
    public String addCategory(@RequestParam String name) {
        Category category = new Category();
        category.setName(name);
        categoryService.save(category);
        return "redirect:/admin";
    }

    @PostMapping("/products/add")
    public String addProduct(@RequestParam("name") String name,
                           @RequestParam("description") String description,
                           @RequestParam("price") Double price,
                           @RequestParam("categoryId") Long categoryId,
                           @RequestParam("deliveryAvailable") Boolean deliveryAvailable,
                           @RequestParam("rating") Double rating,
                           @RequestParam("image") MultipartFile image) {
        try {
            System.out.println("Starting product creation...");
            System.out.println("Name: " + name);
            System.out.println("Description: " + description);
            System.out.println("Price: " + price);
            System.out.println("CategoryId: " + categoryId);
            System.out.println("Delivery Available: " + deliveryAvailable);
            System.out.println("Image name: " + image.getOriginalFilename());

            Category category = categoryService.getCategoryById(categoryId);
            if (category == null) {
                System.out.println("Category not found for id: " + categoryId);
                return "redirect:/admin?error=category_not_found";
            }

            System.out.println("Category found: " + category.getName());

            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setCategory(category);
            product.setDeliveryAvailable(deliveryAvailable);
            product.setRating(rating);
            
            // Создаем директорию, если её нет
            Path uploadPath = Paths.get("uploads");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Сохранение изображения
            if (!image.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                product.setImageUrl("/uploads/" + fileName);
            }
            
            productService.save(product);
            return "redirect:/admin?success=true";
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/admin?error=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
        }
    }
} 