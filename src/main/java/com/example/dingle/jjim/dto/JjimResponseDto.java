package com.example.dingle.jjim.dto;

import com.example.dingle.notice.entity.Notice;
import com.example.dingle.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class JjimResponseDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private long id;
        private User user;
        private Notice notice;
    }
}