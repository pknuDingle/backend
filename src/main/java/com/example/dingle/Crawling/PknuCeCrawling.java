package com.example.dingle.Crawling;

import com.example.dingle.fcm.service.FcmService;
import com.example.dingle.homepage.entity.Homepage;
import com.example.dingle.homepage.service.HomepageService;
import com.example.dingle.keyword.entity.Keyword;
import com.example.dingle.notice.entity.Notice;
import com.example.dingle.notice.repository.NoticeRepository;
import com.example.dingle.noticeKeyword.service.NoticeKeywordService;
import com.example.dingle.personalNotice.service.PersonalNoticeService;
import com.example.dingle.userHomepage.repository.UserHomepageRepository;
import com.example.dingle.userKeyword.repository.UserKeywordRepository;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PknuCeCrawling extends PknuCrawling {

    public PknuCeCrawling(PersonalNoticeService personalNoticeService,
            NoticeKeywordService noticeKeywordService, FcmService fcmService,
            HomepageService homepageService, NoticeRepository noticeRepository,
            UserHomepageRepository userHomepageRepository,
            UserKeywordRepository userKeywordRepository, Filtering filtering) {
        super(personalNoticeService, noticeKeywordService, fcmService, homepageService,
                noticeRepository, userHomepageRepository, userKeywordRepository, filtering);
        homepageName = "부경대학교 컴퓨터·인공지능공학부";
        homepageUrl = "https://ce.pknu.ac.kr/ce/1814";
    }

    @Override
    @Scheduled(fixedDelay = 3600000)
    public void crawling() {
        try {
            Homepage pknuCeHomepage = findHomepage();
            List<Notice> newNotices = new ArrayList<>();
            if (homepageUrl.contains("https://")) {
                setSSL();
            }

            // 공지사항 리스트
            Elements notices = getConnection(homepageUrl).get()
                    .select(".a_bdCont .a_brdList tbody tr:not(.noti)");
            for (Element notice : notices) {
                // 번호, 제목, 링크 가져오기
                String link = homepageUrl + notice.select("td.bdlTitle a").attr("href");
                Long noticeNum = Long.valueOf(notice.select("td.bdlNum.noti").text());
                if (noticeNum <= getLatestNoticeNum()) {
                    continue;
                }

                // 상세페이지 내용 크롤링
                Document doc = getConnection(link).get();
                String title = notice.select("td.bdlTitle a").text();
                String text = doc.select("td.bdvEdit").text();
                String content = text.length() > 30 ? text.substring(0, 30) + "..." : text;

                // 이미지
                Element element = doc.select("td.bdvEdit").first();
                String image = element.select("img").attr("src");
                if (image.isEmpty()) {
                    image = noticeDefaultImage;
                }

                // 공지사항 저장
                newNotices.add(saveNotice(title, content, link, image, noticeNum, pknuCeHomepage));
            }

            // 공지사항에서 키워드 필터링 -> 키워드, 홈페이지 기반 알림
            List<Keyword> keywords = keywordFiltering(newNotices);
            sendKeywordNotification(keywords, newNotices);
            sendHomepageNotification(pknuCeHomepage, newNotices);
        } catch (Exception exception) {
            throw new RuntimeException("크롤링에 실패했습니다.");
        }
    }
}
