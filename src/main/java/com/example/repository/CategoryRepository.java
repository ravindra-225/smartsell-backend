package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.category;

public interface CategoryRepository extends JpaRepository<category, Long>{

}
