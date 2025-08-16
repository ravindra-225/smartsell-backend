package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.Orders;
import com.example.models.Users;

public interface OrderRepository extends JpaRepository<Orders, Long> {
	List<Orders> findByUser(Users user);

}
