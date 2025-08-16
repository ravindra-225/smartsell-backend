package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.models.Products;
import com.example.repository.ProductRepository;

@Service
public class ProductService {
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Products addProduct(Products product) {
		return productRepository.save(product);
	}

	public List<Products> getAllProducts() {
		return productRepository.findAll();
	}

	public Products updateProduct(Long id, Products updatedProduct) {
		Products product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

		product.setTitle(updatedProduct.getTitle());
		product.setDescription(updatedProduct.getDescription());
		product.setPrice(updatedProduct.getPrice());
		product.setLocation(updatedProduct.getLocation());
		product.setCategory(updatedProduct.getCategory());
		product.setSeller(updatedProduct.getSeller());

		return productRepository.save(product);
	}

	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}

	public Optional<Products> getProductById(Long id) {
		return productRepository.findById(id);
	}

	public List<Products> searchByKeyword(String keyword) {
		return productRepository.searchByKeyword(keyword);
	}

	public List<Products> getByCategory(Long categoryId) {
		return productRepository.findByCategoryId(categoryId);
	}

	public List<Products> getByLocation(String location) {
		return productRepository.findByLocationIgnoreCase(location);
	}

	public List<Products> getByPriceRange(double min, double max) {
		return productRepository.findByPriceBetween(min, max);
	}

}
