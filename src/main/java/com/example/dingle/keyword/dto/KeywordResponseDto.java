package com.example.dingle.keyword.dto;

import com.example.dingle.keyword.entity.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Setter;

public class KeywordResponseDto {

    @Setter
    @AllArgsConstructor
    public static class Response {
        private long id;
        private String name;
        private CategoryType categoryType;

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCategoryType() {
            return categoryType.getName();
        }
    }
}