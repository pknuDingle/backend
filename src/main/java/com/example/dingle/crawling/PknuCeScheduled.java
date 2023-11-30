package com.example.dingle.crawling;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PknuCeScheduled {

    private final PknuCe pknuCe;

    // 실시간 실행 테스트(1시간 단위)
    @Scheduled(fixedDelay = 3600000)
    public void pknuMainScheduled() throws Exception {
        pknuCe.pknuCeAllNoticeCrawling();
    }
}