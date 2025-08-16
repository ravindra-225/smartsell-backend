package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.Cart;
import com.example.models.Users;

public interface CartRepository extends JpaRepository<Cart, Long> {
	Optional<Cart> findByUser(Users user);
}
