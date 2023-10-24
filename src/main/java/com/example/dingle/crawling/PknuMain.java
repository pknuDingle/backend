package com.example.dingle.crawling;

import com.example.dingle.notice.entity.Notice;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
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

@Component
public class PknuMain {
    private final String PKNU_MAIN_URL = "https://www.pknu.ac.kr/main/163?action=view&no=";
    private final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";

    public Notice crawling() {
        Notice notice = new Notice();
        String url = "https://www.pknu.ac.kr/main/163?action=view&no=712274";
//        String url = "https://www.pknu.ac.kr/main/163?action=view&no=712273";
        Document document;

        try {
            if(url.indexOf("https://")>=0){
                PknuMain.setSSL();
            }

            Connection conn = Jsoup.connect(url)
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .userAgent(USER_AGENT)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true);
            document = conn.get();

            Elements elementsTitle = document.getElementsByClass("title_b"); // 제목
            Elements elementsContent = document.select("div.bdvTxt"); // 내용

            String title = elementsTitle.get(0).text();
            String content = "";

            for (Element element : elementsContent) {
                content += getContent(element);
            }

            System.out.println("# " + content);

            notice.setTitle(title);
            notice.setContent(content);
            notice.setLink(url);
            notice.setImage("");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }

        return notice;
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
        for(String split : split_P) {
            String[] splits = split.split(">");
            if(splits.length > 1) {
                if(splits[0].contains("href")) {
                    content = content + splits[1] + "[" + splits[0].split("a href=\"")[1].split("\"")[0] + "]";
                }
                else content += splits[1];
            }
        }

        return content.trim();
    }


    // SSL 우회 등록
    public static void setSSL() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }
}