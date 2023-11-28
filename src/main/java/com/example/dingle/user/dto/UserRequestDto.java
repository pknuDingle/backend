package com.example.dingle.user.dto;

import com.example.dingle.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class UserRequestDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post {
        private long id;
        private String name;
        private String email;
        private String imageUrl;
        private long kakakoId;
        private User.Attendance status;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {
        private long id;
        private String name;
        private String email;
        private String imageUrl;
        private long kakakoId;
        private User.Attendance status;
    }
}