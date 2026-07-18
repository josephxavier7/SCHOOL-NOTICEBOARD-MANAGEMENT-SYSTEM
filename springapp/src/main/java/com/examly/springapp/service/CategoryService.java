package com.examly.springapp.service;

import com.examly.springapp.model.Category;
import com.examly.springapp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired private CategoryRepository categoryRepository;

    public Category addCategory(Category category) { return categoryRepository.save(category); }
    public List<Category> getAllCategories() { return categoryRepository.findByIsActiveTrue(); }
    public Optional<Category> getCategoryById(Long id) { return categoryRepository.findById(id); }
    public Category updateCategory(Long id, Category category) {
        category.setId(id);
        return categoryRepository.save(category);
    }
    public void deleteCategory(Long id) {
        categoryRepository.findById(id).ifPresent(c -> { c.setActive(false); categoryRepository.save(c); });
    }
}
