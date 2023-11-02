package com.example.dingle.oauth.kakao;

import com.example.dingle.oauth.jwt.JwtTokenProvider;
import com.example.dingle.user.entity.User;
import com.example.dingle.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/kakao")
public class AuthController {
    private final KakaoAuthService kakaoAuthService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;

    // 카카오 로그인을 위해 회원가입 여부 확인
    //  -> 이미 회원이면 JWT 토큰 발급
    //  -> 가입안되어 있으면 가입 후 JWT 토큰 발급
    @PostMapping
    public ResponseEntity authSignin(@RequestHeader String accessToken) {
        User user = kakaoAuthService.signin(accessToken);
        String jwt = jwtTokenProvider.createToken(String.valueOf(user.getId()));
        KakaoResponseDto.Response response = userMapper.userToKakaoResponseDtoResponse(user, jwt);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}