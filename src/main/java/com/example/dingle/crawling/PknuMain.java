package com.example.dingle.crawling;

import com.example.dingle.homepage.entity.Homepage;
import com.example.dingle.homepage.service.HomepageService;
import com.example.dingle.keyword.entity.Keyword;
import com.example.dingle.notice.entity.Notice;
import com.example.dingle.notice.repository.NoticeRepository;
import com.example.dingle.noticeKeyword.entity.NoticeKeyword;
import com.example.dingle.noticeKeyword.service.NoticeKeywordService;
import com.example.dingle.personalNotice.service.PersonalNoticeService;
import com.example.dingle.userKeyword.entity.UserKeyword;
import com.example.dingle.userKeyword.service.UserKeywordService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

@Component
public class PknuMain {
    private final String PKNU_MAIN_URL = "https://www.pknu.ac.kr/main/163?action=view&no=";
    private final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";

    private final String HOMEPAGE_NAME = "부경대학교 메인홈페이지";

    private final String HOMEPAGE_URL = "https://www.pknu.ac.kr/main/163";

    private final HomepageService homepageService;

    private final NoticeRepository noticeRepository;

    private final Filtering filtering;
    private final UserKeywordService userKeywordService;
    private final NoticeKeywordService noticeKeywordService;

    private final PersonalNoticeService personalNoticeService;

    public PknuMain(HomepageService homepageService, NoticeRepository noticeRepository, Filtering filtering, UserKeywordService userKeywordService, NoticeKeywordService noticeKeywordService, PersonalNoticeService personalNoticeService) {
        this.homepageService = homepageService;
        this.noticeRepository = noticeRepository;
        this.filtering = filtering;
        this.userKeywordService = userKeywordService;
        this.noticeKeywordService = noticeKeywordService;
        this.personalNoticeService = personalNoticeService;
    }

    public void crawling() {
        String url = "https://www.pknu.ac.kr/main/163?cd=10001"; // 메인 홈페이지 링크
        Homepage homepage = findPknuMainHomepage();

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
                String link = HOMEPAGE_URL + element.select("td.bdlTitle a").attr("href"); // 상세페이지 링크
                long pageNum = Long.parseLong(link.split("no=")[1]); // 페이지 번호
                if (pageNum <= latestPageNum) continue; // db에 저장된 가장 최신 공지사항 번호보다 크롤링한 공지사항 번호가 작은 경우 패스
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
                String content = documentDetail.select("div.bdvTxt").get(0).text(); // 내용

                // 이미지
                Element imageElement = documentDetail.select("div.bdvTxt").first();
                String image = imageElement.select("img").attr("src"); // 이미지

                // 공지사항 기본 설정
                notice.setTitle(title); // 제목
                notice.setContent(content); // 내용
                notice.setLink(link); // 상세페에지 링크
                notice.setPageNum(pageNum); // 페이지 번호
                notice.setImage(image);

                // 홈페이지와의 연관관계 생성
                // 부경대 메인 홈페이지 객체 찾기
                notice.setHomepage(homepage);

                // notice 저장
                notice = noticeRepository.save(notice);

                // 키워드 기반 필터링(제목, 내용에서 키워드 추출 -> 키워드 설정한 user에게 알림 전송)
                List<Keyword> keywords = filtering.filterKeywordsReturnKeyWord(title, content);
                noticeKeywordService.createNoticeKeyword(notice, keywords);
                List<UserKeyword> userKeywords = userKeywordService.findUserKeywordsWithKeywords(keywords);
                personalNoticeService.createPersonalNotices(userKeywords, notice);
                // TODO: 알림 전송
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
                    content = content + splits[1] + "[" + splits[0].split("a href=\"")[1].split("\"")[0] + "]";
                } else content += splits[1];
            }
        }

        // 첨부파일 제거
        content = content.replace("$(function(){", "123123lkjlkj").split("123123lkjlkj")[0];

        return content.trim();
    }


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
}