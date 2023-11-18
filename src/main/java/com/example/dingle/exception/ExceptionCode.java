
package com.example.dingle.exception;

import lombok.Getter;

import java.util.Arrays;

public enum ExceptionCode {
    USER_NOT_FOUND(404, "User not found"),
    HOMEPAGE_NOT_FOUND(404, "Homepage not found"),
    USERHOMEPAGE_NOT_FOUND(404, "UserHomepage not found"),
    USERKEYWORD_NOT_FOUND(404, "UserKeyword not found"),
    NOTICE_NOT_FOUND(404, "Notice not found"),
    KEYWORD_NOT_FOUND(404, "Keyword not found"),
    JJIM_NOT_FOUND(404, "Jjim not found"),
    NOTICEKEYWORD_NOT_FOUND(404, "NoticeKeyword not found");


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
