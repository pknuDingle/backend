package com.example.dingle.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class UserRequestDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post {
        private Long id;
    }
}