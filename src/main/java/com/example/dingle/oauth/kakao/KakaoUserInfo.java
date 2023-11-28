package com.example.dingle.oauth.kakao;

import com.example.dingle.oauth.kakaoResponse.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Component
public class KakaoUserInfo { // 카카오 API를 이용해 토큰을 전송하여 유저 정보를 요청

    private static final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";
    private final WebClient webClient;

    public KakaoUserInfoResponse getUserInfo(String token) {
        Flux<KakaoUserInfoResponse> response = webClient.get()
                .uri(USER_INFO_URI)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToFlux(KakaoUserInfoResponse.class);
        return response.blockFirst();
    }
}