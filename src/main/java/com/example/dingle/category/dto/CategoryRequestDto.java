package com.example.dingle.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class CategoryRequestDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post {
        private long id;
        private String name;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {
        private long id;
        private String name;
    }
}