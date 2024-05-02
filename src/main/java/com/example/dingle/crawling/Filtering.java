package com.example.dingle.Crawling;

import com.example.dingle.keyword.entity.Keyword;
import com.example.dingle.keyword.repository.KeywordRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class Filtering {

    private final KeywordRepository keywordRepository;

    public Filtering(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    public List<Keyword> filterKeywords(String title, String content) {
        List<Keyword> keywords = keywordRepository.findAll();
        return keywords.stream()
                .filter(keyword -> title.contains(keyword.getName()) || content.contains(
                        keyword.getName()))
                .collect(Collectors.toList());
    }
}