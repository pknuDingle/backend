package com.example.dingle.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class NoticeResponseDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private long id;
        private String title;
        private String content;
        private String image;
        private String link;
        private boolean isFavorite = false;
    }
}
