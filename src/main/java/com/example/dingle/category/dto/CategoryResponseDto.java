package com.example.dingle.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class CategoryResponseDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private long id;
        private String name;
    }
}