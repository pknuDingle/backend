package com.example.dingle.oauth.kakaoResponse;

import lombok.Getter;

@Getter
public class KakaoProfile {

    private String nickname;
    private String profile_image_url;
    private String thumbnail_image_url;
    private boolean is_default_image;
}