package com.example.dingle.oauth.kakaoResponse;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class KakaoUserInfoResponse {
    private Long id;
    private boolean has_signed_up;
    private LocalDate connected_at;
    private KakaoAccount kakao_account;
}