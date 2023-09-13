package com.example.dingle.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class NoticeRequestDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post {
        private long id;
        private String title;
        private String content;
        private String image;
        private String link;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {
        private long id;
        private String title;
        private String content;
        private String image;
        private String link;
    }
}