package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
