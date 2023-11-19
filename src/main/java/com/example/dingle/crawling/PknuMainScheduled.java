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
    private final NoticeRepository noticeRepository;
    private final PknuMain pknuMain;

    private final Filtering filtering;

    private final NoticeKeywordService noticeKeywordService;

    private static long pageNum = 0;

    public PknuMainScheduled(NoticeRepository noticeRepository, PknuMain pknuMain, Filtering filtering, NoticeKeywordService noticeKeywordService) {
        this.noticeRepository = noticeRepository;
        this.pknuMain = pknuMain;
        this.filtering = filtering;
        this.noticeKeywordService = noticeKeywordService;
    }

    // 실시간 실행 테스트(1시간 단위)
    @Scheduled(fixedDelay = 3600000) // 1초(=1000)
    public void pknuMainScheduled() {
        // 가장 최근에 가져온 페이지 번호 설정
        if(pageNum == 0) {
            List<Notice> notices = noticeRepository.findByHomepage(pknuMain.findPknuMainHomepage());
            if(notices.isEmpty()) pageNum = 713048; // DB가 비어있는 경우
            else pageNum = notices.get(notices.size() - 1).getPageNum();
        }

        // 삭제된 공지사항을 대비해서 최근 저장한 공지사항에서 3번째까지 비교
        int count = 1;
        while(count < 4) {
            count = noticeCheck(count);
        }
    }

    // 빈 페이지 경우를 대비해서 만듦
    public int noticeCheck(int count) {
        Notice notice = pknuMain.crawling(pageNum + count);

        // 새로운 공지사항이 있는 경우
        if(notice != null) {
            noticeRepository.save(notice);

            // 키워드 설정
            List<Keyword> keywords = filtering.filterKeywordsReturnKeyWord(notice.getTitle(), notice.getContent());
            noticeKeywordService.createNoticeKeyword(notice, keywords);

            // TODO: 알림 전송



            pageNum += count;
            return 10;
        } else {
            System.out.println("!! pageNum : " + pageNum);
        }

        return count+1;
    }
}