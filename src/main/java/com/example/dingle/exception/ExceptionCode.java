
package com.example.dingle.exception;
import lombok.Getter;

import java.util.Arrays;

public enum ExceptionCode {
    USER_NOT_FOUND(404, "User not found"),
    MAJOR_NOT_FOUND(404, "Major not found"),
    USERMAJOR_NOT_FOUND(404, "UserMajor not found"),
    NOTICEMAJOR_NOT_FOUND(404, "NoticeMajor not found"),
    USERCATEGORY_NOT_FOUND(404, "UserCategory not found");


    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }

    public static ExceptionCode findByMessage(String message) {
        return Arrays.stream(ExceptionCode.values())
                .filter(exceptionCode -> exceptionCode.getMessage().equals(message))
                .findFirst()
                .orElse(null);
    }
}
