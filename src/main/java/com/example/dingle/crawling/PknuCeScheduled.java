package com.example.dingle.crawling;

import com.example.dingle.noticeKeyword.service.NoticeKeywordService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PknuCeScheduled {
    private final PknuCe pknuCe;
    private final Filtering filtering;
    private final NoticeKeywordService noticeKeywordService;

    public PknuCeScheduled(PknuCe pknuCe, Filtering filtering, NoticeKeywordService noticeKeywordService) {
        this.pknuCe = pknuCe;
        this.filtering = filtering;
        this.noticeKeywordService = noticeKeywordService;
    }

    // 실시간 실행 테스트(1시간 단위)
    @Scheduled(fixedDelay = 3600000)
    public void pknuMainScheduled() throws Exception {
        pknuCe.pknuCeAllNoticeCrawling();
    }
}