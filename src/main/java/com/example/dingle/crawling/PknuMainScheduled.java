package com.example.dingle.crawling;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PknuMainScheduled {

    private final PknuMain pknuMain;

    // 실시간 실행 테스트(1시간 단위)
    @Scheduled(fixedDelay = 3600000) // 1초(=1000)
    public void pknuMainScheduled() throws Exception {
        pknuMain.crawling();
    }
}