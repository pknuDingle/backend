package com.example.dingle.Crawling;

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
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;

public abstract class PknuCrawling {

    private final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";
    protected String homepageName, homepageUrl;
    protected PersonalNoticeService personalNoticeService;
    protected NoticeKeywordService noticeKeywordService;
    protected FcmService fcmService;
    protected HomepageService homepageService;
    protected NoticeRepository noticeRepository;
    protected UserHomepageRepository userHomepageRepository;
    protected UserKeywordRepository userKeywordRepository;
    protected Filtering filtering;
    @Value("${file-server.domain} + ':' + ${server.port} + ${file-server.image.notice-default}")
    protected String noticeDefaultImage;

    protected PknuCrawling(PersonalNoticeService personalNoticeService,
            NoticeKeywordService noticeKeywordService, FcmService fcmService,
            HomepageService homepageService, NoticeRepository noticeRepository,
            UserHomepageRepository userHomepageRepository,
            UserKeywordRepository userKeywordRepository, Filtering filtering) {
        this.personalNoticeService = personalNoticeService;
        this.noticeKeywordService = noticeKeywordService;
        this.fcmService = fcmService;
        this.homepageService = homepageService;
        this.noticeRepository = noticeRepository;
        this.userHomepageRepository = userHomepageRepository;
        this.userKeywordRepository = userKeywordRepository;
        this.filtering = filtering;
    }

    public void setSSL() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[]{
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
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    public Connection getConnection(String pageUrl) {
        return Jsoup.connect(pageUrl)
                .header("Content-Type", "application/json;charset=UTF-8")
                .userAgent(USER_AGENT)
                .method(Connection.Method.GET)
                .ignoreContentType(true);
    }

    public Long getLatestNoticeNum() {
        return noticeRepository.findLatestPageNum();
    }

    public Notice saveNotice(String title, String content, String link, String image,
            Long noticeNum, Homepage homepage) {
        return noticeRepository.save(
                Notice.createWithPageNum(title, content, link, image, noticeNum, homepage));
    }

    public List<Keyword> keywordFiltering(List<Notice> notices) {
        List<Keyword> keywords = new ArrayList<>();
        for (Notice notice : notices) {
            keywords = filtering.filterKeywords(notice.getTitle(),
                    notice.getContent());
            noticeKeywordService.createNoticeKeyword(notice, keywords);
        }

        return keywords;
    }

    public void sendKeywordNotification(List<Keyword> keywords, List<Notice> notices)
            throws Exception {
        for (Notice notice : notices) {
            List<UserKeyword> userKeywords = userKeywordRepository.findAllByKeywordIn(keywords);
            List<PersonalNotice> personalKeywordNotices = personalNoticeService.createPersonalNoticesWithUserKeywords(
                    userKeywords, notice);
            fcmService.sendPersonalKeywordNoticeToUser(personalKeywordNotices);
        }
    }

    public void sendHomepageNotification(Homepage homepage, List<Notice> notices) throws Exception {
        for (Notice notice : notices) {
            List<UserHomepage> userHomepages = userHomepageRepository.findAllByHomepage(homepage);
            List<PersonalNotice> personalHomepageNotices = personalNoticeService.createPersonalNoticesWithUserHomepages(
                    userHomepages, notice);
            fcmService.sendPersonalHomepageNoticeToUser(personalHomepageNotices);
        }
    }


    public Homepage findHomepage() {
        List<Homepage> homepages = homepageService.findAllHomepages();
        Homepage mainHomepage = null;
        for (Homepage homepage : homepages) {
            if (homepage.getName().equals(homepageName)) {
                mainHomepage = homepage;
            }
        }
        if (mainHomepage == null) { // 부경대 메인 홈페이지가 없는 경우 -> 새로 생성하기
            mainHomepage = new Homepage();
            mainHomepage.setName(homepageName);
            mainHomepage.setUrl(homepageUrl);
            mainHomepage = homepageService.createHomepage(mainHomepage);
        }

        return mainHomepage;
    }

    public abstract void crawling();
}
