package com.example.dingle.crawling;

import com.example.dingle.keyword.entity.Keyword;
import com.example.dingle.keyword.repository.KeywordRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class Filtering {

    private final KeywordRepository keywordRepository;

    public Filtering(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    public List<Long> filterKeywords(String title, String content) {
        // 카테고리 목록들을 순회하며, 키워드 찾기
        List<Keyword> keywords = keywordRepository.findAll();
        return keywords.stream()
                .filter(keyword -> title.contains(keyword.getName()) || content.contains(keyword.getName()))
                .map(Keyword::getId)
                .collect(Collectors.toList());
    }

    public List<Keyword> filterKeywordsReturnKeyWord(String title, String content) {
        // 카테고리 목록들을 순회하며, 키워드 찾기
        List<Keyword> keywords = keywordRepository.findAll();
        return keywords.stream()
                .filter(keyword -> title.contains(keyword.getName()) || content.contains(keyword.getName()))
                .collect(Collectors.toList());
    }
}