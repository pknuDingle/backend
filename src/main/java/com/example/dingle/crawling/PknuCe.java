package com.example.dingle.crawling;

import com.example.dingle.fcm.service.FcmService;
import com.example.dingle.homepage.entity.Homepage;
import com.example.dingle.homepage.service.HomepageService;
import com.example.dingle.keyword.entity.Keyword;
import com.example.dingle.notice.entity.Notice;
import com.example.dingle.notice.repository.NoticeRepository;
import com.example.dingle.noticeKeyword.service.NoticeKeywordService;
import com.example.dingle.personalNotice.entity.PersonalNotice;
import com.example.dingle.personalNotice.service.PersonalNoticeService;
import com.example.dingle.userHomepage.entity.UserHomepage;
import com.example.dingle.userHomepage.repository.UserHomepageRepository;
import com.example.dingle.userKeyword.entity.UserKeyword;
import com.example.dingle.userKeyword.repository.UserKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PknuCe {

    private final static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";
    private final static String HOMEPAGE_NAME = "부경대학교 컴퓨터·인공지능공학부";
    private final static String HOMEPAGE_URL = "https://ce.pknu.ac.kr/ce/1814";
    private final static String IMAGE_URL = "https://ce.pknu.ac.kr/";
    private final NoticeRepository noticeRepository;
    private final Filtering filtering;
    private final UserKeywordRepository userKeywordRepository;
    private final PersonalNoticeService personalNoticeService;
    private final NoticeKeywordService noticeKeywordService;
    private final FcmService fcmService;
    private final UserHomepageRepository userHomepageRepository;
    private final HomepageService homepageService;

    @Value("${file-server.domain} + ':' + ${server.port} + ${file-server.image.notice-default}")
    private String noticeDefaultImage;

    // SSL 우회 등록
    public static void setSSL() throws Exception {
        TrustManager[] trustAllCerts = {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());

        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    public void pknuCeAllNoticeCrawling() throws Exception {
        Homepage pknuCeHomepage = findPknuCeHomepage();
        if (HOMEPAGE_URL.indexOf("https://") >= 0) {
            PknuCe.setSSL();
        }

        Connection listConnection, elementConnection;
        listConnection = Jsoup.connect(HOMEPAGE_URL).header("Content-Type", "application/json;charset=UTF-8").userAgent(USER_AGENT).method(Connection.Method.GET).ignoreContentType(true);

        // db에 저장된 가장 최신 공지사항 번호
        Long latestNoticeNum = noticeRepository.findLatestNoticeNum();

        // 공지사항 리스트
        Elements notices = listConnection.get().select(".a_bdCont .a_brdList tbody tr:not(.noti)");
        for (Element notice : notices) {
            // 번호, 제목, 링크 가져오기
            Long noticeNum = Long.valueOf(notice.select("td.bdlNum.noti").text());
            if (noticeNum <= latestNoticeNum) continue; // db에 저장된 가장 최신 공지사항 번호보다 크롤링한 공지사항 번호가 작은 경우 패스
            String title = notice.select("td.bdlTitle a").text();
            String link = HOMEPAGE_URL + notice.select("td.bdlTitle a").attr("href");

            // 링크를 통해 내용 일부 가져오기
            elementConnection = Jsoup.connect(link).header("Content-Type", "application/json;charset=UTF-8").userAgent(USER_AGENT).method(Connection.Method.GET).ignoreContentType(true);
            Document doc = Jsoup.parse(elementConnection.get().html());
            String text = doc.select("td.bdvEdit").text();
            String content = text.length() > 30 ? text.substring(0, 30) + "..." : text;

            // 이미지
            Element element = doc.select("td.bdvEdit").first();
            String imageSrc = element.select("img").attr("src");
            String image = noticeDefaultImage;
            if (!imageSrc.isEmpty()) {
                image = IMAGE_URL + imageSrc;
            }

            // 저장
            Notice newNotice = noticeRepository.save(Notice.createWithNoticeNum(title, content, link, image, noticeNum, pknuCeHomepage));

            // 키워드 기반 필터링
            List<Keyword> keywords = filtering.filterKeywordsReturnKeyWord(title, content);
            noticeKeywordService.createNoticeKeyword(newNotice, keywords);

            // 설정된 키워드 공지사항 알림
            List<UserKeyword> userKeywords = userKeywordRepository.findAllByKeywordIn(keywords);
            List<PersonalNotice> personalKeywordNotices = personalNoticeService.createPersonalNoticesWithUserKeywords(userKeywords, newNotice);
            fcmService.sendPersonalKeywordNoticeToUser(personalKeywordNotices);

            // 설정된 홈페이지 공지사항 알림
            List<UserHomepage> userHomepages = userHomepageRepository.findAllByHomepage(pknuCeHomepage);
            List<PersonalNotice> personalHomepageNotices = personalNoticeService.createPersonalNoticesWithUserHomepages(userHomepages, newNotice);
            fcmService.sendPersonalHomepageNoticeToUser(personalHomepageNotices);
        }
    }

    // 연관관계 있는 홈페이지 반환
    public Homepage findPknuCeHomepage() {
        List<Homepage> homepages = homepageService.findAllHomepages();
        Homepage pknuCeHomepage = null;
        for (Homepage homepage : homepages) {
            if (homepage.getName().equals(HOMEPAGE_NAME)) pknuCeHomepage = homepage;
        }
        if (pknuCeHomepage == null) { // 부경대 메인 홈페이지가 없는 경우 -> 새로 생성하기
            pknuCeHomepage = new Homepage();
            pknuCeHomepage.setName(HOMEPAGE_NAME);
            pknuCeHomepage.setUrl(HOMEPAGE_URL);
            pknuCeHomepage = homepageService.createHomepage(pknuCeHomepage);
        }

        return pknuCeHomepage;
    }
}
