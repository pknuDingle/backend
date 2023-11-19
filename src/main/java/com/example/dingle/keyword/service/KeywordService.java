package com.example.dingle.keyword.service;

import com.example.dingle.keyword.entity.Keyword;
import com.example.dingle.keyword.repository.KeywordRepository;
import com.example.dingle.exception.BusinessLogicException;
import com.example.dingle.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;

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

    // Update
    public Keyword updateKeyword(Keyword keyword) {
        Keyword findKeyword = verifiedKeyword(keyword.getId());

        Optional.ofNullable(keyword.getName()).ifPresent(findKeyword::setName);

        return keywordRepository.save(findKeyword);
    }

    // Delete
    public void deleteKeyword(long keywordId) {
        Keyword keyword = verifiedKeyword(keywordId);
        keywordRepository.delete(keyword);
    }

    // 증명
    public Keyword verifiedKeyword(long keywordId) {
        Optional<Keyword> keyword = keywordRepository.findById(keywordId);
        return keyword.orElseThrow(() -> new BusinessLogicException(ExceptionCode.KEYWORD_NOT_FOUND));
    }
}
