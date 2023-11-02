package com.example.dingle.oauth.kakao;

import com.example.dingle.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class KakaoResponseDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private long id;
        private String name;
        private String email;
        private String imageUrl;
        private long kakakoId;
        private User.Attendance status;
        private String jwt;
    }
}
