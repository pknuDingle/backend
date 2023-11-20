package com.example.dingle.userHomepage.service;

import com.example.dingle.exception.BusinessLogicException;
import com.example.dingle.exception.ExceptionCode;
import com.example.dingle.homepage.entity.Homepage;
import com.example.dingle.keyword.entity.Keyword;
import com.example.dingle.user.entity.User;
import com.example.dingle.userHomepage.entity.UserHomepage;
import com.example.dingle.userHomepage.repository.UserHomepageRepository;
import com.example.dingle.userKeyword.entity.UserKeyword;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserHomepageService {
    private final UserHomepageRepository userHomepageRepository;

    // Create
    public UserHomepage createUserHomepage(User user, Homepage homepage) {
        UserHomepage userHomepage = homepage.toUserHomepage(user);
        return userHomepageRepository.save(userHomepage);
    }

    public void createUserHomepage(User user, List<Homepage> homepages) {
        // 새로운 키워드 생성
        for(Homepage homepage : homepages) {
            if(verifiedUserHomepage(user, homepage) == null) createUserHomepage(user, homepage);
        }

        // 이전 키워드 제거
        List<Homepage> beforeHomepages = userHomepageRepository.findAllByUser(user)
                .stream()
                .map(UserHomepage::getHomepage)
                .collect(Collectors.toList());
        beforeHomepages.removeAll(homepages);
        for(Homepage homepage : beforeHomepages) {
            UserHomepage userHomepage = verifiedUserHomepage(user, homepage);
            userHomepageRepository.delete(userHomepage);
        }
    }

    // Read
    public UserHomepage findUserHomepage(long userHomepageId) {
        return verifiedUserHomepage(userHomepageId);
    }

    public List<Homepage> findHomepageByUser(User user) {
        return userHomepageRepository.findAllByUser(user).stream().map(UserHomepage::getHomepage).collect(Collectors.toList());
    }

    // Delete
    public void deleteUserHomepage(long userHomepageId) {
        UserHomepage userHomepage = verifiedUserHomepage(userHomepageId);
        userHomepageRepository.delete(userHomepage);
    }

    public void deleteUserHomepage(UserHomepage userHomepage) {
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

    public UserHomepage verifiedUserHomepage(User user, Homepage homepage) {
        Optional<UserHomepage> userHomepage = userHomepageRepository.findByUserAndHomepage(user, homepage);
        return userHomepage.orElse(null);
    }

}
