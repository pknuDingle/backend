package com.example.dingle.util;

import com.example.dingle.exception.*;
import com.example.dingle.user.entity.User;
import com.example.dingle.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@AllArgsConstructor
public class FindUserByJWT {
    // 로그인한 직원 가져오기
    public User getLoginUser() {
        //SecurityContextHolder에서 회원정보 가져오기
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
}
