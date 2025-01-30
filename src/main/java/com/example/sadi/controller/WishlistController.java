package com.example.sadi.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.sadi.model.User;
import com.example.sadi.service.UserService;
import com.example.sadi.service.WishlistService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;
    private final UserService userService;

    @GetMapping
    public String viewWishlist(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("wishlistItems", wishlistService.getWishlistItems(user));
        return "wishlist/view";
    }

    @PostMapping("/remove/{productId}")
    @ResponseBody
    public String removeFromWishlist(@PathVariable Long productId, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        wishlistService.removeFromWishlist(user, productId);
        return "success";
    }
} 