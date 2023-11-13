package com.example.dingle.user.service;

import com.example.dingle.exception.BusinessLogicException;
import com.example.dingle.exception.ExceptionCode;
import com.example.dingle.user.dto.UserRequestDto;
import com.example.dingle.user.entity.User;
import com.example.dingle.user.repository.UserRepository;
import com.example.dingle.util.FindUserByJWT;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FindUserByJWT findUserByJWT;

    // Create
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Read
    public User findUser(long userId) {
        return verifiedUser(userId);
    }

    public User findUser() {
        return findUserByJWT.getLoginUser();
    }

    // Update
    public User updateUser(User user) {
        User findUser = verifiedUser(user.getId());

        Optional.ofNullable(user.getName()).ifPresent(findUser::setName);
        Optional.ofNullable(user.getEmail()).ifPresent(findUser::setEmail);
        Optional.ofNullable(user.getImageUrl()).ifPresent(findUser::setImageUrl);
        Optional.ofNullable(user.getStatus()).ifPresent(findUser::setStatus);
        if(user.getKakakoId() != 0) findUser.setKakakoId(user.getKakakoId());

        return userRepository.save(findUser);
    }

    // Delete
    public void deleteUser(long userId) {
        User user = verifiedUser(userId);
        userRepository.delete(user);
    }

    // 증명
    public User verifiedUser(long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
    }
}
