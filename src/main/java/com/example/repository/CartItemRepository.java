package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

}
