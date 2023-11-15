package com.example.dingle.category.mapper;

import com.example.dingle.category.dto.CategoryRequestDto;
import com.example.dingle.category.dto.CategoryResponseDto;
import com.example.dingle.category.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category categoryRequestDtoPostToCategory(CategoryRequestDto.Post post);

    Category categoryRequestDtoPatchToCategory(CategoryRequestDto.Patch patch);

    CategoryResponseDto.Response categoryToCategoryResponseDtoResponse(Category category);
}
