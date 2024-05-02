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
        private String name;
        private String email;
        private String imageUrl;
        private long kakakoId;
        private User.Attendance status;
    }
}
