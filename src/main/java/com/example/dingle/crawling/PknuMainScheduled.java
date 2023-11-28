package com.example.dingle.crawling;


import com.example.dingle.keyword.entity.Keyword;
import com.example.dingle.notice.entity.Notice;
import com.example.dingle.notice.repository.NoticeRepository;
import com.example.dingle.noticeKeyword.service.NoticeKeywordService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//@AllArgsConstructor
public class PknuMainScheduled {
    private final PknuMain pknuMain;

    public PknuMainScheduled(PknuMain pknuMain) {
        this.pknuMain = pknuMain;
    }

    // 실시간 실행 테스트(1시간 단위)
    @Scheduled(fixedDelay = 3600000) // 1초(=1000)
    public void pknuMainScheduled() {
        pknuMain.crawling();
    }
}