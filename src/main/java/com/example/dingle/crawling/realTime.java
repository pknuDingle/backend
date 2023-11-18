package com.example.dingle.crawling;


import com.example.dingle.notice.entity.Notice;
import com.example.dingle.notice.repository.NoticeRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//@AllArgsConstructor
public class realTime {
    private final NoticeRepository noticeRepository;
    private static long pageNum = 0;

    public realTime(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    // 실시간 실행 테스트(1시간 단위)
//    @Scheduled(fixedDelay = 3600000) // 1초(=1000)
//    public void test() {
//        // 가장 최근에 가져온 페이지 번호 설정
//        if(pageNum == 0) {
//            List<Notice> notices = noticeRepository.findAll();
//            if(notices.isEmpty()) pageNum = 712274; // DB가 비어있는 경우
//            else pageNum = notices.get(notices.size() - 1).getPageNum();
//        }
//
//        int count = 1;
//        while(count < 4) {
//            count = noticeCheck(count);
//        }
//    }

    // 빈 페이지 경우를 대비해서 만듦
    public int noticeCheck(int count) {
        PknuMain pknuMain = new PknuMain();
        Notice notice = pknuMain.crawling(pageNum + count);

        if(notice != null) {
            noticeRepository.save(notice);
            pageNum += count;
            return 10;
        } else {
            System.out.println("!! pageNum : " + pageNum);
        }

        return count+1;
    }
}
