package com.example.dingle.userMajor.service;

import com.example.dingle.exception.BusinessLogicException;
import com.example.dingle.exception.ExceptionCode;
import com.example.dingle.userMajor.entity.UserMajor;
import com.example.dingle.userMajor.repository.UserMajorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserMajorService {
    private final UserMajorRepository userMajorRepository;

    // Create
    public UserMajor createUserMajor(UserMajor userMajor) {
        return userMajorRepository.save(userMajor);
    }

    // Read
    public UserMajor findUserMajor(long userMajorId) {
        return verifiedUserMajor(userMajorId);
    }


    // Delete
    public void deleteUserMajor(long userMajorId) {
        UserMajor userMajor = verifiedUserMajor(userMajorId);
        userMajorRepository.delete(userMajor);
    }

    // 증명
    public UserMajor verifiedUserMajor(long userMajorId) {
        Optional<UserMajor> userMajor = userMajorRepository.findById(userMajorId);
        return userMajor.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USERMAJOR_NOT_FOUND));
    }
}
