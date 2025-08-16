package com.example.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.models.Cart;
import com.example.models.CartItem;
import com.example.models.OrderItem;
import com.example.models.Orders;
import com.example.models.Users;
import com.example.repository.CartRepository;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
    private OrderItemRepository orderItemRepository;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private CartRepository cartRepository;
	
	 @Transactional
	    public Orders placeOrder(String userEmail) {
		 Users user = userRepository.findByEmail(userEmail)
	                .orElseThrow(() -> new RuntimeException("User not found for email: " + userEmail));

	        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Cart is empty"));

	        if (cart.getItems() == null || cart.getItems().isEmpty()) {
	            throw new RuntimeException("Cart is empty");
	        }

	        Orders order = new Orders();
	        order.setUser(user);
	        order.setOrderDate(LocalDateTime.now());
	        order.setStatus("PLACED");

	        double totalAmount = 0;
	        order = orderRepository.save(order);
	        List<OrderItem> orderItems = new ArrayList<>();
	        
	        for (CartItem cartItem : cart.getItems()) {
	            OrderItem orderItem = new OrderItem();
	            orderItem.setOrder(order);
	            orderItem.setProduct(cartItem.getProduct());
	            orderItem.setQuantity(cartItem.getQuantity());
	            orderItem.setPrice(cartItem.getProduct().getPrice());

	            totalAmount += orderItem.getPrice() * orderItem.getQuantity();

	            orderItems.add(orderItem);
	        }
	        order.setOrderItems(orderItems);
	        order.setTotalAmount(totalAmount);
	        orderRepository.save(order);

	        // Clear the cart
	        cart.getItems().clear();
	        cartRepository.save(cart);

	        return order;
	    }

	    public List<Orders> getOrderHistory(String email) {
	    	Users user = userRepository.findByEmail(email)
	                .orElseThrow(() -> new RuntimeException("User not found for email: " + email));
	        return orderRepository.findByUser(user);
	    }
}
