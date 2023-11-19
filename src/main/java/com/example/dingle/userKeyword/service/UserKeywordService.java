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
    public UserKeyword createUserKeyword(User currentUser, Keyword keyword) {
        UserKeyword userKeyword = keyword.toUserKeyword(currentUser);
        return userKeywordRepository.save(userKeyword);
    }

    // Read
    public UserKeyword findUserKeyword(long userKeywordId) {
        return verifiedUserKeyword(userKeywordId);
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

    public List<UserKeyword> findUserKeywordsWithKeywordIds(List<Long> keywordIds) {
        List<UserKeyword> userKeywords = keywordIds.stream()
                .flatMap(keywordId -> userKeywordRepository.findAllByKeywordId(keywordId).stream())
                .collect(Collectors.toList());

        return userKeywords;
    }
}
