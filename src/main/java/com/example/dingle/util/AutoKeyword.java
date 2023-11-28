package com.example.dingle.util;

import com.example.dingle.keyword.entity.CategoryType;
import com.example.dingle.keyword.entity.Keyword;
import com.example.dingle.keyword.service.KeywordService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AutoKeyword {
    private final KeywordService keywordService;
    private final List<String> SCHOOLS = List.of(
            "근로",
            "졸업",
            "등록",
            "장학",
            "수강신청",
            "성적",
            "생활관",
            "동아리",
            "휴학"
    );
    private final List<String> CAREER_SUPPORTS = List.of(
            "취업",
            "창업",
            "교육",
            "공모전",
            "실습",
            "비교과",
            "교직",
            "어학"
    );

    public AutoKeyword(KeywordService keywordService) {
        this.keywordService = keywordService;
        saveKeyword();
    }

    // 키워드 저장
    public void saveKeyword() {
        List<Keyword> keywords = keywordService.findAllKeywords();
        if (keywords.isEmpty()) { // 키워드가 없을 때에만 키워드 생성
            for (String categoryType : SCHOOLS) {
                Keyword keyword = new Keyword();
                keyword.setName(categoryType);
                keyword.setCategoryType(CategoryType.SCHOOL);
                keywordService.createKeyword(keyword);
            }

            for (String categoryType : CAREER_SUPPORTS) {
                Keyword keyword = new Keyword();
                keyword.setName(categoryType);
                keyword.setCategoryType(CategoryType.CAREER_SUPPORT);
                keywordService.createKeyword(keyword);
            }
        }
    }
}