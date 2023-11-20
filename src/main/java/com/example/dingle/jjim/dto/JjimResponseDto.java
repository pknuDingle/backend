package com.example.dingle.jjim.dto;

import com.example.dingle.notice.entity.Notice;
import com.example.dingle.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class JjimResponseDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private long id;
        private long userId;
        private long noticeId;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}