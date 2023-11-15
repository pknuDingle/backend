package com.example.dingle.category.controller;

import com.example.dingle.category.dto.CategoryRequestDto;
import com.example.dingle.category.dto.CategoryResponseDto;
import com.example.dingle.category.entity.Category;
import com.example.dingle.category.mapper.CategoryMapper;
import com.example.dingle.category.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/category")
@Validated
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    // Create
    @PostMapping
    public ResponseEntity postCategory(@Valid @RequestBody CategoryRequestDto.Post post) {
        Category category = categoryService.createCategory(categoryMapper.categoryRequestDtoPostToCategory(post));
        CategoryResponseDto.Response response = categoryMapper.categoryToCategoryResponseDtoResponse(category);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Read
    @GetMapping("/{category-id}")
    public ResponseEntity getCategory(@Positive @PathVariable("category-id") long categoryId) {
        Category category = categoryService.findCategory(categoryId);
        CategoryResponseDto.Response response = categoryMapper.categoryToCategoryResponseDtoResponse(category);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update
    @PutMapping("/{category-id}")
    public ResponseEntity patchCategory(@Positive @PathVariable("category-id") long categoryId,
                                        @Valid @RequestBody CategoryRequestDto.Patch patch) {
        patch.setId(categoryId);
        Category category = categoryService.updateCategory(categoryMapper.categoryRequestDtoPatchToCategory(patch));
        CategoryResponseDto.Response response = categoryMapper.categoryToCategoryResponseDtoResponse(category);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Delete
    @DeleteMapping("/{category-id}")
    public ResponseEntity deleteCategory(@Positive @PathVariable("category-id") long categoryId) {
        categoryService.deleteCategory(categoryId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
