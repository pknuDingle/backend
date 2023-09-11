package com.example.dingle.user.dto;

import com.example.dingle.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class UserResponseDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private long id;
        private String email;
        private String image;
        private String token;
        private User.Attendance status;
    }
}
