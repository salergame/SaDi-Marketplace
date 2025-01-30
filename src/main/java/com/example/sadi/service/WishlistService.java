package com.example.sadi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sadi.model.Product;
import com.example.sadi.model.User;
import com.example.sadi.model.WishlistItem;
import com.example.sadi.repository.WishlistRepository;

@Service
@Transactional
public class WishlistService {
    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public void addToWishlist(User user, Product product) {
        if (wishlistRepository.findByUserAndProduct(user, product).isEmpty()) {
            WishlistItem item = new WishlistItem();
            item.setUser(user);
            item.setProduct(product);
            wishlistRepository.save(item);
        }
    }

    public List<WishlistItem> getWishlistItems(User user) {
        return wishlistRepository.findByUser(user);
    }

    public void removeFromWishlist(User user, Product product) {
        wishlistRepository.findByUserAndProduct(user, product)
                         .ifPresent(wishlistRepository::delete);
    }

    public boolean isInWishlist(User user, Product product) {
        return wishlistRepository.findByUserAndProduct(user, product).isPresent();
    }
} 