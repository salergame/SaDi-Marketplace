package com.example.sadi.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.sadi.model.Product;
import com.example.sadi.model.User;
import com.example.sadi.service.CartService;
import com.example.sadi.service.ProductService;
import com.example.sadi.service.UserService;
import com.example.sadi.service.WishlistService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CartService cartService;
    private final UserService userService;
    private final WishlistService wishlistService;

    public ProductController(ProductService productService, CartService cartService, UserService userService, WishlistService wishlistService) {
        this.productService = productService;
        this.cartService = cartService;
        this.userService = userService;
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products/list";
    }

    @PostMapping
    public String createProduct(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}")
    public String showProduct(@PathVariable Long id, Model model) {
        try {
            Product product = productService.getProductById(id);
            if (product == null) {
                return "redirect:/?error=product_not_found";
            }
            model.addAttribute("product", product);
            return "product/details";
        } catch (Exception e) {
            log.error("Error showing product details", e);
            return "redirect:/?error=internal_error";
        }
    }

    @PostMapping("/{id}/addToCart")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addToCart(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") Integer quantity,
            Principal principal) {
        
        try {
            if (principal == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Not authenticated"));
            }

            User user = userService.findByUsername(principal.getName());
            Product product = productService.getProductById(id);
            
            if (product == null) {
                return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", "Product not found"));
            }

            cartService.addToCart(user, product, quantity);
            return ResponseEntity.ok(Collections.singletonMap("message", "Added to cart"));
            
        } catch (Exception e) {
            log.error("Error adding product to cart", e);
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/{id}/addToWishlist")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addToWishlist(
            @PathVariable Long id,
            Principal principal) {
        
        try {
            if (principal == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Not authenticated"));
            }

            User user = userService.findByUsername(principal.getName());
            Product product = productService.getProductById(id);
            
            if (product == null) {
                return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", "Product not found"));
            }

            wishlistService.addToWishlist(user, product);
            return ResponseEntity.ok(Collections.singletonMap("message", "Added to wishlist"));
            
        } catch (Exception e) {
            log.error("Error adding product to wishlist", e);
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}