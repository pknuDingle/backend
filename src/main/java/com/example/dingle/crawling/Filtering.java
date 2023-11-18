package com.example.dingle.crawling;

import com.example.dingle.category.entity.Category;
import com.example.dingle.category.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Filtering {

    private final CategoryRepository categoryRepository;

    public Filtering(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Long> filterKeywordsInTitle(String title) {
        // 카테고리 목록들을 순회하며, 키워드 찾기
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .filter(category -> title.contains(category.getName()))
                .map(Category::getId)
                .collect(Collectors.toList());
    }

}
