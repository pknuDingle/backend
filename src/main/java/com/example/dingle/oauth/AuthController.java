package com.example.dingle.oauth;

import com.example.dingle.oauth.jwt.JwtTokenProvider;
import com.example.dingle.user.service.UserService;
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
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final KakaoAuthService kakaoAuthService;
    private final JwtTokenProvider jwtTokenProvider;

    // 카카오 로그인을 위해 회원가입 여부 확인, 이미 회원이면 Jwt 토큰 발급
//    @PostMapping("/login")
//    public ApiResponse<HashMap<Long, String>> authCheck(@RequestHeader String accessToken) {
//        Long userId = kakaoAuthService.isSignedUp(accessToken); // 유저 고유번호 추출
//        HashMap<Long, String> map = new HashMap<>();
//        map.put(userId, jwtTokenProvider.createToken(userId.toString()));
//        return ApiResponse.success(map, ResponseCode.USER_LOGIN_SUCCESS.getMessage());
//    }
    @PostMapping("/login")
    public ResponseEntity<HashMap<Long, String>> authCheck(@RequestHeader String accessToken) {
        Long userId = kakaoAuthService.isSignedUp(accessToken); // 유저 고유번호 추출
        HashMap<Long, String> map = new HashMap<>();
        map.put(userId, jwtTokenProvider.createToken(userId.toString()));
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }
}