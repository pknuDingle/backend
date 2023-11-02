package com.example.dingle.oauth.kakaoResponse;

import com.example.dingle.oauth.kakaoResponse.KakaoAccount;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class KakaoUserInfoResponse {
    private Long id;
    private boolean has_signed_up;
    private LocalDate connected_at;
    private KakaoAccount kakao_account;
}