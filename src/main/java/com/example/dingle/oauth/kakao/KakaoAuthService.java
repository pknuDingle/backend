package com.example.dingle.oauth.kakao;

import com.example.dingle.exception.BusinessLogicException;
import com.example.dingle.exception.ExceptionCode;
import com.example.dingle.oauth.kakao.KakaoUserInfo;
import com.example.dingle.oauth.kakaoResponse.KakaoUserInfoResponse;
import com.example.dingle.user.entity.User;
import com.example.dingle.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class KakaoAuthService {

    private final KakaoUserInfo kakaoUserInfo;
    private final UserRepository userRepository;

//    @Transactional(readOnly = true)
//    public Long login(String token) {
//        KakaoUserInfoResponse userInfo = kakaoUserInfo.getUserInfo(token);
//        User user = userRepository.findByKakakoId(userInfo.getId()).orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
//        return user.getId();
//    }

    public User signin(String token) {
        KakaoUserInfoResponse userInfo = kakaoUserInfo.getUserInfo(token);

//        System.out.println("-".repeat(20));
//        System.out.println(userInfo.getId());
//        System.out.println(userInfo.getKakao_account().getProfile().getNickname());
//        System.out.println(userInfo.getKakao_account().getProfile().getProfile_image_url());
//        System.out.println(userInfo.getKakao_account().getProfile().getThumbnail_image_url());
//        System.out.println(userInfo.getKakao_account().getProfile().is_default_image());
//        System.out.println("-".repeat(20));

        User user = userRepository.findByKakakoId(userInfo.getId()).orElseGet(() -> {
            User user1 = new User();
            user1.setKakakoId(userInfo.getId());
            user1.setName(userInfo.getKakao_account().getProfile().getNickname());
            user1.setImageUrl(userInfo.getKakao_account().getProfile().getThumbnail_image_url());
            return userRepository.save(user1);
        });
        return user;
    }
}