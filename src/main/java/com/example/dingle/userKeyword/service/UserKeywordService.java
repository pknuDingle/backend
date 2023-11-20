package com.example.dingle.userKeyword.service;

import com.example.dingle.keyword.entity.Keyword;
import com.example.dingle.exception.BusinessLogicException;
import com.example.dingle.exception.ExceptionCode;
import com.example.dingle.user.entity.User;
import com.example.dingle.userKeyword.entity.UserKeyword;
import com.example.dingle.userKeyword.repository.UserKeywordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserKeywordService {
    private final UserKeywordRepository userKeywordRepository;

    // Create
    public UserKeyword createUserKeyword(User user, Keyword keyword) {
        UserKeyword userKeyword = keyword.toUserKeyword(user);
        return userKeywordRepository.save(userKeyword);
    }

    public void createUserKeywords(User user, List<Keyword> keywords) {
        // 새로운 키워드 생성
        for (Keyword keyword : keywords) {
            if (verifiedUserKeyword(user, keyword) == null) createUserKeyword(user, keyword);
        }

        // 이전 키워드 제거
        List<Keyword> beforeKeywords = userKeywordRepository.findAllByUser(user)
                .stream()
                .map(UserKeyword::getKeyword)
                .collect(Collectors.toList());
        beforeKeywords.removeAll(keywords);
        for (Keyword keyword : beforeKeywords) {
            UserKeyword userKeyword = verifiedUserKeyword(user, keyword);
            userKeywordRepository.delete(userKeyword);
        }
    }

    // Read
    public UserKeyword findUserKeyword(long userKeywordId) {
        return verifiedUserKeyword(userKeywordId);
    }

    public List<Keyword> findKeywordByUser(User user) {
        return userKeywordRepository.findAllByUser(user).stream().map(UserKeyword::getKeyword).collect(Collectors.toList());
    }

    // Delete
    public void deleteUserKeyword(long userKeywordId) {
        UserKeyword userKeyword = verifiedUserKeyword(userKeywordId);
        userKeywordRepository.delete(userKeyword);
    }

    @Transactional
    public void deleteAllUserKeyword(User currentUser) {
        userKeywordRepository.deleteAllByUserId(currentUser.getId());
    }

    // 증명
    public UserKeyword verifiedUserKeyword(long userKeywordId) {
        Optional<UserKeyword> userKeyword = userKeywordRepository.findById(userKeywordId);
        return userKeyword.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USERKEYWORD_NOT_FOUND));
    }

    public UserKeyword verifiedUserKeyword(User user, Keyword keyword) {
        Optional<UserKeyword> userKeyword = userKeywordRepository.findAllByUserAndKeyword(user, keyword);
        return userKeyword.orElse(null);
    }

    public List<UserKeyword> findUserKeywordsWithKeywords(List<Keyword> keywords) {
        List<UserKeyword> userKeywords = keywords.stream()
                .flatMap(keyword -> userKeywordRepository.findAllByKeyword(keyword).stream())
                .collect(Collectors.toList());

        return userKeywords;
    }
}
