package com.example.sadi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sadi.model.Product;
import com.example.sadi.model.User;
import com.example.sadi.model.WishlistItem;
import com.example.sadi.repository.WishlistItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistItemRepository wishlistRepository;

    public void addToWishlist(User user, Product product) {
        if (wishlistRepository.findByUserAndProduct(user, product) == null) {
            WishlistItem item = new WishlistItem();
            item.setUser(user);
            item.setProduct(product);
            wishlistRepository.save(item);
        }
    }

    public List<WishlistItem> getWishlistItems(User user) {
        return wishlistRepository.findByUser(user);
    }

    public void removeFromWishlist(User user, Long productId) {
        wishlistRepository.deleteByUserAndProductId(user, productId);
    }

    public boolean isInWishlist(User user, Product product) {
        return wishlistRepository.findByUserAndProduct(user, product) != null;
    }
} 