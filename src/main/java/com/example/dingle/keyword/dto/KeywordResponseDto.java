package com.example.dingle.keyword.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class KeywordResponseDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private long id;
        private String name;
    }
}