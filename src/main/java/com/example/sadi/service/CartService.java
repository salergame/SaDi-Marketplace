package com.example.sadi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sadi.model.CartItem;
import com.example.sadi.model.Product;
import com.example.sadi.model.User;
import com.example.sadi.repository.CartRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void addToCart(User user, Product product, Integer quantity) {
        Optional<CartItem> existingItem = cartRepository.findByUserAndProduct(user, product);
        
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setUser(user);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cartRepository.save(newItem);
        }
    }

    public List<CartItem> getCartItems(User user) {
        return cartRepository.findByUser(user);
    }

    public void removeFromCart(User user, Product product) {
        cartRepository.findByUserAndProduct(user, product)
                     .ifPresent(cartRepository::delete);
    }

    public void updateQuantity(User user, Product product, Integer quantity) {
        cartRepository.findByUserAndProduct(user, product)
                     .ifPresent(item -> {
                         item.setQuantity(quantity);
                         cartRepository.save(item);
                     });
    }
} 