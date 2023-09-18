
package com.example.dingle.exception;

import lombok.Getter;

import java.util.Arrays;

public enum ExceptionCode {
    USER_NOT_FOUND(404, "User not found"),
    NOTICE_NOT_FOUND(404, "Notice not found"),
    CATEGORY_NOT_FOUND(404, "Category not found"),
    JJIM_NOT_FOUND(404, "Jjim not found"),
    NOTICECATEGORY_NOT_FOUND(404, "NoticeCategory not found");


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
