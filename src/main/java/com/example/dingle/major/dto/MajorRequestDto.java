package com.example.dingle.major.dto;

import com.example.dingle.major.entity.Major;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class MajorRequestDto {
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