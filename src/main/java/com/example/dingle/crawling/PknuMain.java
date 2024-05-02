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
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PknuMain {

    private final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";
    private final String HOMEPAGE_NAME = "부경대학교 메인홈페이지";
    private final String HOMEPAGE_URL = "https://www.pknu.ac.kr/main/163";
    private final HomepageService homepageService;
    private final NoticeRepository noticeRepository;
    private final Filtering filtering;
    private final NoticeKeywordService noticeKeywordService;
    private final PersonalNoticeService personalNoticeService;
    private final FcmService fcmService;
    private final UserHomepageRepository userHomepageRepository;
    private final UserKeywordRepository userKeywordRepository;
    @Value("${file-server.domain} + ':' + ${server.port} + ${file-server.image.notice-default}")
    private String noticeDefaultImage;

    // SSL 우회 등록
    public static void setSSL() throws NoSuchAlgorithmException, KeyManagementException {
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

    public void crawling() throws Exception {
        String url = "https://www.pknu.ac.kr/main/163?cd=10001"; // 메인 홈페이지 링크
        Homepage mainHomepage = findPknuMainHomepage();

        try {
            if (url.indexOf("https://") >= 0) {
                PknuMain.setSSL();
            }

            Connection connection = Jsoup.connect(url)
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .userAgent(USER_AGENT)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true);
            Document document = connection.get();

            // db에 저장된 가장 최신 공지사항 번호
            Long latestPageNum = noticeRepository.findLatestPageNum();

            // 공지사항 내용 조회
            Elements elementsContent = document.select(".brdList tbody tr:not(.noti)"); // 학과
            for (Element element : elementsContent) {
                Notice notice = new Notice();
                // 페이지 번호
                String link =
                        HOMEPAGE_URL + element.select("td.bdlTitle a").attr("href"); // 상세페이지 링크
                long pageNum = Long.parseLong(link.split("no=")[1]); // 페이지 번호
                if (pageNum <= latestPageNum) {
                    continue; // db에 저장된 가장 최신 공지사항 번호보다 크롤링한 공지사항 번호가 작은 경우 패스
                }
//                if(pageNum != 713128) continue;

                // 상세페이지 내용 크롤링
                Connection connectionDetail = Jsoup.connect(link)
                        .header("Content-Type", "application/json;charset=UTF-8")
                        .userAgent(USER_AGENT)
                        .method(Connection.Method.GET)
                        .ignoreContentType(true);
                Document documentDetail = connectionDetail.get();

                // 제목 & 내용 크롤링
                String title = documentDetail.getElementsByClass("title_b").get(0).text(); // 제목
                String text = documentDetail.select("div.bdvTxt").get(0).text(); // 내용
                String content = text.length() > 30 ? text.substring(0, 30) + "..." : text;

                // 이미지
                Element imageElement = documentDetail.select("div.bdvTxt").first();
                String image = imageElement.select("img").attr("src"); // 이미지
                if (image.isEmpty()) {
                    image = noticeDefaultImage;
                }

                // 공지사항 저장
                Notice newNotice = noticeRepository.save(
                        Notice.createWithPageNum(title, content, link, image, pageNum,
                                mainHomepage));

                // 키워드 기반 필터링
                List<Keyword> keywords = filtering.filterKeywordsReturnKeyWord(title, content);
                noticeKeywordService.createNoticeKeyword(newNotice, keywords);

                // 설정된 키워드 공지사항 알림
                List<UserKeyword> userKeywords = userKeywordRepository.findAllByKeywordIn(keywords);
                List<PersonalNotice> personalKeywordNotices = personalNoticeService.createPersonalNoticesWithUserKeywords(
                        userKeywords, newNotice);
                fcmService.sendPersonalKeywordNoticeToUser(personalKeywordNotices);

                // 설정된 홈페이지 공지사항 알림
                List<UserHomepage> userHomepages = userHomepageRepository.findAllByHomepage(
                        mainHomepage);
                List<PersonalNotice> personalHomepageNotices = personalNoticeService.createPersonalNoticesWithUserHomepages(
                        userHomepages, newNotice);
                fcmService.sendPersonalHomepageNoticeToUser(personalHomepageNotices);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    // 연관관계 있는 홈페이지 반환
    public Homepage findPknuMainHomepage() {
        List<Homepage> homepages = homepageService.findAllHomepages();
        Homepage mainHomepage = null;
        for (Homepage homepage : homepages) {
            if (homepage.getName().equals(HOMEPAGE_NAME)) {
                mainHomepage = homepage;
            }
        }
        if (mainHomepage == null) { // 부경대 메인 홈페이지가 없는 경우 -> 새로 생성하기
            mainHomepage = new Homepage();
            mainHomepage.setName(HOMEPAGE_NAME);
            mainHomepage.setUrl(HOMEPAGE_URL);
            mainHomepage = homepageService.createHomepage(mainHomepage);
        }

        return mainHomepage;
    }

    // content 분류
    public String getContent(Element element) {
        String html = element.toString();
        String content = "";
        html = html.replace("<br>", "").replace("<p>", "").replace("</font>", "");
        html = html.replace("</span>", "").replace("</p>", "").replace("</b>", "");
        html = html.replace("&nbsp;", " ").replace("&lt;", "<").replace("&gt;", ">");
        html = html.replace("><", "");

        String[] split_P = html.split("<");
        for (String split : split_P) {
            String[] splits = split.split(">");
            if (splits.length > 1) {
                if (splits[0].contains("href")) {
                    content = content + splits[1] + "[" + splits[0].split("a href=\"")[1].split(
                            "\"")[0] + "]";
                } else {
                    content += splits[1];
                }
            }
        }

        // 첨부파일 제거
        content = content.replace("$(function(){", "123123lkjlkj").split("123123lkjlkj")[0];

        return content.trim();
    }
}