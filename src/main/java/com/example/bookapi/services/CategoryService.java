package com.example.bookapi.services;

import com.example.bookapi.dtos.CategoryDTO;
import com.example.bookapi.dtos.UpdateCategoryDTO;
import com.example.bookapi.entities.Category;
import com.example.bookapi.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryDTO(category.getId(), category.getName(), category.getDescription()))
                .toList();
    }

    public Optional<CategoryDTO> getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(category -> new CategoryDTO(category.getId(), category.getName(), category.getDescription()));
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.name());
        category.setDescription(categoryDTO.description());
        category = categoryRepository.save(category);
        return new CategoryDTO(category.getId(), category.getName(), category.getDescription());
    }

    public Optional<CategoryDTO> updateCategory(Long id, UpdateCategoryDTO updateCategoryDTO) {
        return categoryRepository.findById(id).map(category -> {
            category.setName(updateCategoryDTO.name());
            category.setDescription(updateCategoryDTO.description());
            category = categoryRepository.save(category);
            return new CategoryDTO(category.getId(), category.getName(), category.getDescription());
        });
    }

    public boolean deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

