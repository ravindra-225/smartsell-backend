package com.example.service;

import com.example.models.*;
import com.example.repository.*;
import org.springframework.stereotype.Service;

@Service
public class CartService {
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;

	public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository,
			ProductRepository productRepository, UserRepository userRepository) {
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
	}

	public Cart getCartByUserEmail(String email) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found for email: " + email));
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

	public Cart addItemToCart(String email, Long productId, int quantity) {
		Cart cart = getCartByUserEmail(email);
		Products product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));

		// Check if product already in cart
		CartItem existingItem = cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId))
				.findFirst().orElse(null);

		if (existingItem != null) {
			existingItem.setQuantity(existingItem.getQuantity() + quantity);
			cartItemRepository.save(existingItem);
		} else {
			CartItem newItem = new CartItem();
			newItem.setCart(cart);
			newItem.setProduct(product);
			newItem.setQuantity(quantity);
			cart.getItems().add(newItem);
			cartItemRepository.save(newItem);
		}
		return cartRepository.save(cart);
	}

	public Cart removeItemFromCart(String email, Long productId) {
		Cart cart = getCartByUserEmail(email);

		CartItem itemToRemove = cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId))
				.findFirst().orElseThrow(() -> new RuntimeException("Product not found in cart"));

		cart.getItems().remove(itemToRemove);
		cartItemRepository.delete(itemToRemove);

		return cartRepository.save(cart);
	}
}
