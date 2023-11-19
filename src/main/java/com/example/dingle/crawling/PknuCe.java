package com.example.dingle.crawling;

import com.example.dingle.keyword.repository.KeywordRepository;
import com.example.dingle.notice.entity.Notice;
import com.example.dingle.notice.repository.NoticeRepository;
import com.example.dingle.noticeKeyword.repository.NoticeKeywordRepository;
import com.example.dingle.personalNotice.service.PersonalNoticeService;
import com.example.dingle.userKeyword.entity.UserKeyword;
import com.example.dingle.userKeyword.repository.UserKeywordRepository;
import com.example.dingle.userKeyword.service.UserKeywordService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;

@Component
public class PknuCe {
    private final static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";
    private String pknuCeAllNoticeUrl = "https://ce.pknu.ac.kr/ce/1814";
    private final NoticeRepository noticeRepository;
    private final NoticeKeywordRepository noticeKeywordRepository;
    private final KeywordRepository keywordRepository;
    private final Filtering filtering;
    private final UserKeywordService userKeywordService;
    private final PersonalNoticeService personalNoticeService;

    public PknuCe(NoticeRepository noticeRepository,
                  NoticeKeywordRepository noticeKeywordRepository,
                  KeywordRepository keywordRepository, Filtering filtering, PersonalNoticeService personalNoticeService, UserKeywordRepository userKeywordRepository, UserKeywordService userKeywordService, PersonalNoticeService personalNoticeService1) {
        this.noticeRepository = noticeRepository;
        this.noticeKeywordRepository = noticeKeywordRepository;
        this.keywordRepository = keywordRepository;
        this.filtering = filtering;
        this.userKeywordService = userKeywordService;
        this.personalNoticeService = personalNoticeService1;
    }

    // TODO: 다시 불러올 때, 새로운 공지사항만 추가해야 함.
    public void pknuCeAllNoticeCrawling() throws Exception {
        if (pknuCeAllNoticeUrl.indexOf("https://") >= 0) {
            PknuCe.setSSL();
        }

        Connection listConnection, elementConnection;
        listConnection = Jsoup.connect(pknuCeAllNoticeUrl).header("Content-Type", "application/json;charset=UTF-8").userAgent(USER_AGENT).method(Connection.Method.GET).ignoreContentType(true);

        // 공지사항 리스트
        Elements notices = listConnection.get().select(".a_bdCont .a_brdList tbody tr:not(.noti)");
        for (Element notice : notices) {
            // 제목, 링크 가져오기
            String title = notice.select("td.bdlTitle a").text();
            String link = pknuCeAllNoticeUrl + notice.select("td.bdlTitle a").attr("href");

            // 링크를 통해 내용 일부 가져오기
            elementConnection = Jsoup.connect(link).header("Content-Type", "application/json;charset=UTF-8").userAgent(USER_AGENT).method(Connection.Method.GET).ignoreContentType(true);
            Document doc = Jsoup.parse(elementConnection.get().html());
            String text = doc.select("td.bdvEdit").text();
            String content = text.length() > 30 ? text.substring(0, 30) + "..." : text;

            // 저장
            Notice newNotice = noticeRepository.save(new Notice(title, content, link));

            // 키워드 기반 필터링(제목, 내용에서 키워드 추출 -> 키워드 설정한 user에게 알림 전송)
            List<Long> keywordIds = filtering.filterKeywords(title, content);
            List<UserKeyword> userKeywords = userKeywordService.findUserKeywordsWithKeywordIds(keywordIds);
            personalNoticeService.createPersonalNotices(userKeywords, newNotice);
            // TODO: 알림 전송
        }
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
}
