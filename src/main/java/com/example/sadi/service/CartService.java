package com.example.sadi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sadi.model.CartItem;
import com.example.sadi.model.Product;
import com.example.sadi.model.User;
import com.example.sadi.repository.CartItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartItemRepository;

    public void addToCart(User user, Product product, Integer quantity) {
        CartItem existingItem = cartItemRepository.findByUserAndProduct(user, product);
        
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setUser(user);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cartItemRepository.save(newItem);
        }
    }

    public List<CartItem> getCartItems(User user) {
        return cartItemRepository.findByUser(user);
    }

    public void removeFromCart(User user, Long productId) {
        cartItemRepository.deleteByUserAndProductId(user, productId);
    }

    public void updateQuantity(User user, Long productId, Integer quantity) {
        CartItem item = cartItemRepository.findByUserAndProductId(user, productId);
        if (item != null) {
            item.setQuantity(quantity);
            cartItemRepository.save(item);
        }
    }

    public Double getCartTotal(User user) {
        return cartItemRepository.findByUser(user).stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }
} 