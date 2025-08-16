package com.example.controller;

import com.example.models.Cart;
import com.example.service.CartService;

import java.security.Principal;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	 private final CartService cartService;

	    public CartController(CartService cartService) {
	        this.cartService = cartService;
	    }

	    @GetMapping
	    public Cart getCart(Principal principal) {
	        return cartService.getCartByUserEmail(principal.getName());
	    }

	    @PostMapping("/add")
	    public Cart addItem(@RequestParam Long productId,
	                        @RequestParam int quantity,
	                        Principal principal) {
	        return cartService.addItemToCart(principal.getName(), productId, quantity);
	    }

	    @DeleteMapping("/remove")
	    public Cart removeItem(@RequestParam Long productId,
	                           Principal principal) {
	        return cartService.removeItemFromCart(principal.getName(), productId);
	    }
}
