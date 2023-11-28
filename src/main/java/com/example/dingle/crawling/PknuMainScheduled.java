package com.example.dingle.crawling;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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