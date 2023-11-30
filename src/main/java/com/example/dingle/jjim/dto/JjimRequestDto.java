package com.example.dingle.jjim.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class JjimRequestDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post {
        private long id;
        private long userId;
        private long noticeId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {
        private long id;
        private long userId;
        private long noticeId;
    }
}