package com.examly.springapp.controller;

import com.examly.springapp.model.Category;
import com.examly.springapp.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Category add(@RequestBody Category category) { return categoryService.addCategory(category); }

    @GetMapping
    public List<Category> getAll() { return categoryService.getAllCategories(); }

    @GetMapping("/{id}")
    public Optional<Category> getById(@PathVariable Long id) { return categoryService.getCategoryById(id); }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category category) { return categoryService.updateCategory(id, category); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { categoryService.deleteCategory(id); }
}
