package com.example.dingle.oauth.kakao;

import com.example.dingle.oauth.jwt.JwtTokenProvider;
import com.example.dingle.user.entity.User;
import com.example.dingle.user.mapper.UserMapper;
import com.example.dingle.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RequiredArgsConstructor
@RestController
@RequestMapping("/kakao")
public class AuthController {
    private final KakaoAuthService kakaoAuthService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;
    private final UserService userService;

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

    // token 사용을 위한 테스크 과정
    @PostMapping("/test/{userId}")
    public ResponseEntity generate(@Positive @PathVariable long userId) {
        User user = userService.verifiedUser(userId);
        String jwt = jwtTokenProvider.createToken(String.valueOf(userId));
        KakaoResponseDto.Response response = userMapper.userToKakaoResponseDtoResponse(user, jwt);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    // fcm 토큰 값 추가된 로그인, 추후 프론트에서 fcm 토큰 생성 후 전송 구현 시 사용할 예정
    @PostMapping("/login")
    public ResponseEntity authSigninWithFcmToken(@RequestHeader String accessToken, @RequestHeader String fcmToken) {
        User user = kakaoAuthService.signinWithFcmToken(accessToken, fcmToken);
        String jwt = jwtTokenProvider.createToken(String.valueOf(user.getId()));
        KakaoResponseDto.Response response = userMapper.userToKakaoResponseDtoResponse(user, jwt);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}