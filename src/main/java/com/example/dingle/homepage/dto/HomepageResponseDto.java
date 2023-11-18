package com.example.dingle.homepage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class HomepageResponseDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private long id;
        private String name;
        private String url;
    }
}
