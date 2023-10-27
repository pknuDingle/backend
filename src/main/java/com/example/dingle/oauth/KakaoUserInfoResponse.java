package com.example.dingle.oauth;

import lombok.Getter;

@Getter
public class KakaoUserInfoResponse {

    private Long id;
    private boolean hasSignedUp;
    private KakaoAccount kakaoAccount;
}