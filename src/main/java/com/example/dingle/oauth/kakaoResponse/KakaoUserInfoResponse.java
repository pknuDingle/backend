package com.example.dingle.oauth.kakaoResponse;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class KakaoUserInfoResponse {

    private Long id;
    private boolean has_signed_up;
    private LocalDate connected_at;
    private KakaoAccount kakao_account;
}