package com.example.dingle.category.service;

import com.example.dingle.category.entity.Category;
import com.example.dingle.category.repository.CategoryRepository;
import com.example.dingle.exception.BusinessLogicException;
import com.example.dingle.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    // Create
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Read
    public Category findCategory(long categoryId) {
        return verifiedCategory(categoryId);
    }

    // Update
    public Category updateCategory(Category category) {
        Category findCategory = verifiedCategory(category.getId());

        Optional.ofNullable(category.getName()).ifPresent(findCategory::setName);

        return categoryRepository.save(findCategory);
    }

    // Delete
    public void deleteCategory(long categoryId) {
        Category category = verifiedCategory(categoryId);
        categoryRepository.delete(category);
    }

    // 증명
    public Category verifiedCategory(long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        return category.orElseThrow(() -> new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND));
    }
}
