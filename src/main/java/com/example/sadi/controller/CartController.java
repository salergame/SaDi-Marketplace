package com.example.sadi.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.sadi.model.User;
import com.example.sadi.service.CartService;
import com.example.sadi.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @GetMapping
    public String viewCart(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("cartItems", cartService.getCartItems(user));
        model.addAttribute("total", cartService.getCartTotal(user));
        return "cart/view";
    }

    @PostMapping("/update/{productId}")
    @ResponseBody
    public String updateQuantity(@PathVariable Long productId, 
                               @RequestParam Integer quantity,
                               Principal principal) {
        User user = userService.findByUsername(principal.getName());
        cartService.updateQuantity(user, productId, quantity);
        return "success";
    }

    @PostMapping("/remove/{productId}")
    @ResponseBody
    public String removeItem(@PathVariable Long productId, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        cartService.removeFromCart(user, productId);
        return "success";
    }
} 