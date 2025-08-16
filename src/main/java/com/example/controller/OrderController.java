package com.example.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.models.Orders;
import com.example.service.OrderService;

@RestController
@RequestMapping("/api/orders")

public class OrderController {
	private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @PostMapping("/place")
    public Orders placeOrder(Principal principal) {
        return orderService.placeOrder(principal.getName());
    }

    @GetMapping("/history")
    public List<Orders> getOrderHistory(Principal principal) {
        return orderService.getOrderHistory(principal.getName());
    }
}
