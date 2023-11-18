package com.example.dingle.userHomepage.service;

import com.example.dingle.exception.BusinessLogicException;
import com.example.dingle.exception.ExceptionCode;
import com.example.dingle.homepage.entity.Homepage;
import com.example.dingle.user.entity.User;
import com.example.dingle.userHomepage.entity.UserHomepage;
import com.example.dingle.userHomepage.repository.UserHomepageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserHomepageService {
    private final UserHomepageRepository userHomepageRepository;

    // Create
    public UserHomepage createUserHomepage(User currentUser, Homepage homepage) {
        UserHomepage userHomepage = homepage.toUserHomepage(currentUser);
        return userHomepageRepository.save(userHomepage);
    }

    // Read
    public UserHomepage findUserHomepage(long userHomepageId) {
        return verifiedUserHomepage(userHomepageId);
    }


    // Delete
    public void deleteUserHomepage(long userHomepageId) {
        UserHomepage userHomepage = verifiedUserHomepage(userHomepageId);
        userHomepageRepository.delete(userHomepage);
    }

    @Transactional
    public void deleteAllUserHomepage(User currentUser) {
        userHomepageRepository.deleteAllByUserId(currentUser.getId());
    }

    // 증명
    public UserHomepage verifiedUserHomepage(long userHomepageId) {
        Optional<UserHomepage> userHomepage = userHomepageRepository.findById(userHomepageId);
        return userHomepage.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USERHOMEPAGE_NOT_FOUND));
    }
}
