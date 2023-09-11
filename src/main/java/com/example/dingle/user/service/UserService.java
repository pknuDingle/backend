package com.example.dingle.user.service;

import com.example.dingle.exception.BusinessLogicException;
import com.example.dingle.exception.ExceptionCode;
import com.example.dingle.user.dto.UserRequestDto;
import com.example.dingle.user.entity.User;
import com.example.dingle.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // Create
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Read
    public User findUser(long userId) {
        return verifiedUser(userId);
    }

    // Update
    public User updateUser(User user) {
        User findUser = verifiedUser(user.getId());

        Optional.ofNullable(user.getEmail()).ifPresent(findUser::setEmail);
        Optional.ofNullable(user.getImage()).ifPresent(findUser::setImage);
        Optional.ofNullable(user.getToken()).ifPresent(findUser::setToken);
        Optional.ofNullable(user.getStatus()).ifPresent(findUser::setStatus);


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
