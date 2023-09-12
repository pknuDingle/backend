package com.example.dingle.major.dto;

import com.example.dingle.major.entity.Major;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class MajorResponseDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private long id;
        private String name;
    }
}
