package com.example.dingle.keyword.service;

import com.example.dingle.exception.BusinessLogicException;
import com.example.dingle.exception.ExceptionCode;
import com.example.dingle.keyword.entity.Keyword;
import com.example.dingle.keyword.repository.KeywordRepository;
import com.example.dingle.user.entity.User;
import com.example.dingle.userKeyword.service.UserKeywordService;
import com.example.dingle.util.FindUserByJWT;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;
    private final UserKeywordService userKeywordService;
    private final FindUserByJWT findUserByJWT;

    // Create
    public Keyword createKeyword(Keyword keyword) {
        return keywordRepository.save(keyword);
    }

    // Read
    public Keyword findKeyword(long keywordId) {
        return verifiedKeyword(keywordId);
    }

    public List<Keyword> findAllKeywords() {
        return keywordRepository.findAll();
    }

    public List<Keyword> findAllByUser() {
        User user = findUserByJWT.getLoginUser();
        List<Keyword> keywords = userKeywordService.findKeywordByUser(user);
        return keywords;
    }

    // Update
    public Keyword updateKeyword(Keyword keyword) {
        Keyword findKeyword = verifiedKeyword(keyword.getId());

        Optional.ofNullable(keyword.getName()).ifPresent(findKeyword::setName);

        return keywordRepository.save(findKeyword);
    }

    public void updateKeyword(List<String> keywords) {
        List<Keyword> keywords1 = keywords.stream()
                .map(this::verifiedKeyword)
                .collect(Collectors.toList());

        User user = findUserByJWT.getLoginUser();
        userKeywordService.createUserKeywords(user, keywords1);
    }

    // Delete
    public void deleteKeyword(long keywordId) {
        Keyword keyword = verifiedKeyword(keywordId);
        keywordRepository.delete(keyword);
    }

    // 증명
    public Keyword verifiedKeyword(long keywordId) {
        Optional<Keyword> keyword = keywordRepository.findById(keywordId);
        return keyword.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.KEYWORD_NOT_FOUND));
    }

    public Keyword verifiedKeyword(String name) {
        Optional<Keyword> keyword = keywordRepository.findByName(name);
        return keyword.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.KEYWORD_NOT_FOUND));
    }
}
