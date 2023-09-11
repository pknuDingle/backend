package com.example.dingle.user.dto;

import com.example.dingle.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class UserRequestDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post {
        private long id;
        private String email;
        private String image;
        private String token;
        private User.Attendance status;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {
        private long id;
        private String email;
        private String image;
        private String token;
        private User.Attendance status;
    }
}