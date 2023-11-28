package com.example.dingle.crawling;

import com.example.dingle.homepage.entity.Homepage;
import com.example.dingle.homepage.service.HomepageService;
import com.example.dingle.keyword.entity.Keyword;
import com.example.dingle.keyword.repository.KeywordRepository;
import com.example.dingle.notice.entity.Notice;
import com.example.dingle.notice.repository.NoticeRepository;
import com.example.dingle.noticeKeyword.repository.NoticeKeywordRepository;
import com.example.dingle.noticeKeyword.service.NoticeKeywordService;
import com.example.dingle.personalNotice.service.PersonalNoticeService;
import com.example.dingle.userKeyword.entity.UserKeyword;
import com.example.dingle.userKeyword.service.UserKeywordService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;

@Component
public class PknuCe {
    private final static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";
    private final static String HOMEPAGE_NAME = "부경대학교 컴퓨터·인공지능공학부";
    private final static String HOMEPAGE_URL = "https://ce.pknu.ac.kr/ce/1814";
    private final static String IMAGE_URL = "https://ce.pknu.ac.kr/";
    private final NoticeRepository noticeRepository;
    private final NoticeKeywordRepository noticeKeywordRepository;
    private final KeywordRepository keywordRepository;
    private final Filtering filtering;
    private final UserKeywordService userKeywordService;
    private final PersonalNoticeService personalNoticeService;
    private final NoticeKeywordService noticeKeywordService;
    private final HomepageService homepageService;
    private final String pknuCeAllNoticeUrl = "https://ce.pknu.ac.kr/ce/1814";

    public PknuCe(NoticeRepository noticeRepository, NoticeKeywordRepository noticeKeywordRepository, KeywordRepository keywordRepository, Filtering filtering, UserKeywordService userKeywordService, PersonalNoticeService personalNoticeService, NoticeKeywordService noticeKeywordService, HomepageService homepageService) {
        this.noticeRepository = noticeRepository;
        this.noticeKeywordRepository = noticeKeywordRepository;
        this.keywordRepository = keywordRepository;
        this.filtering = filtering;
        this.userKeywordService = userKeywordService;
        this.personalNoticeService = personalNoticeService;
        this.noticeKeywordService = noticeKeywordService;
        this.homepageService = homepageService;
    }

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
        Homepage homepage = findPknuMainHomepage();
        if (pknuCeAllNoticeUrl.indexOf("https://") >= 0) {
            PknuCe.setSSL();
        }

        Connection listConnection, elementConnection;
        listConnection = Jsoup.connect(pknuCeAllNoticeUrl).header("Content-Type", "application/json;charset=UTF-8").userAgent(USER_AGENT).method(Connection.Method.GET).ignoreContentType(true);

        // db에 저장된 가장 최신 공지사항 번호
        Long latestNoticeNum = noticeRepository.findLatestNoticeNum();

        // 공지사항 리스트
        Elements notices = listConnection.get().select(".a_bdCont .a_brdList tbody tr:not(.noti)");
        for (Element notice : notices) {
            // 번호, 제목, 링크 가져오기
            Long noticeNum = Long.valueOf(notice.select("td.bdlNum.noti").text());
            if (noticeNum <= latestNoticeNum) continue; // db에 저장된 가장 최신 공지사항 번호보다 크롤링한 공지사항 번호가 작은 경우 패스
            String title = notice.select("td.bdlTitle a").text();
            String link = pknuCeAllNoticeUrl + notice.select("td.bdlTitle a").attr("href");

            // 링크를 통해 내용 일부 가져오기
            elementConnection = Jsoup.connect(link).header("Content-Type", "application/json;charset=UTF-8").userAgent(USER_AGENT).method(Connection.Method.GET).ignoreContentType(true);
            Document doc = Jsoup.parse(elementConnection.get().html());
            String text = doc.select("td.bdvEdit").text();
            String content = text.length() > 30 ? text.substring(0, 30) + "..." : text;

            // 이미지
            Element element = doc.select("td.bdvEdit").first();
            String image = IMAGE_URL + element.select("img").attr("src"); // 이미지

            // 저장
            Notice newNotice = new Notice();
            newNotice.setHomepage(homepage);
            newNotice.setNoticeNum(noticeNum);
            newNotice.setLink(link);
            newNotice.setTitle(title);
            newNotice.setContent(content);
            newNotice.setImage(image);
            newNotice = noticeRepository.save(newNotice);

            // 키워드 기반 필터링(제목, 내용에서 키워드 추출 -> 키워드 설정한 user에게 알림 전송)
            List<Keyword> keywords = filtering.filterKeywordsReturnKeyWord(title, text);
            noticeKeywordService.createNoticeKeyword(newNotice, keywords);
            List<UserKeyword> userKeywords = userKeywordService.findUserKeywordsWithKeywords(keywords);
            personalNoticeService.createPersonalNotices(userKeywords, newNotice);
            // TODO: 알림 전송
        }
    }

    // 연관관계 있는 홈페이지 반환
    public Homepage findPknuMainHomepage() {
        List<Homepage> homepages = homepageService.findAllHomepages();
        Homepage mainHomepage = null;
        for (Homepage homepage : homepages) {
            if (homepage.getName().equals(HOMEPAGE_NAME)) mainHomepage = homepage;
        }
        if (mainHomepage == null) { // 부경대 메인 홈페이지가 없는 경우 -> 새로 생성하기
            mainHomepage = new Homepage();
            mainHomepage.setName(HOMEPAGE_NAME);
            mainHomepage.setUrl(HOMEPAGE_URL);
            mainHomepage = homepageService.createHomepage(mainHomepage);
        }

        return mainHomepage;
    }
}
