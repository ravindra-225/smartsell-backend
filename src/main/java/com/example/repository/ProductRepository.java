package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.models.Products;

public interface ProductRepository extends JpaRepository<Products, Long>  {
	 // Search by title or description (case-insensitive)
    @Query("SELECT p FROM Products p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Products> searchByKeyword(@Param("keyword") String keyword);

    // Filter by category
    List<Products> findByCategoryId(Long categoryId);

    // Filter by location
    List<Products> findByLocationIgnoreCase(String location);

    // Filter by price range
    @Query("SELECT p FROM Products p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Products> findByPriceBetween(@Param("minPrice") double minPrice,
                                   @Param("maxPrice") double maxPrice);
}
