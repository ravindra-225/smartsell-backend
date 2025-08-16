package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.models.category;
import com.example.repository.CategoryRepository;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;
	
	public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
	
	public category addCategory(category category) {
        return categoryRepository.save(category);
    }

    public List<category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public category updateCategory(Long id, category updatedCategory) {
        category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(updatedCategory.getName());
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
