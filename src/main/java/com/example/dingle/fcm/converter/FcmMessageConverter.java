package com.example.dingle.fcm.converter;

import com.example.dingle.personalNotice.entity.PersonalNotice;
import com.google.firebase.messaging.MulticastMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FcmMessageConverter {

    public MulticastMessage personalKeywordNoticeToMessage(PersonalNotice personalNotice, List<String> fcmTokens) {
        return MulticastMessage.builder()
                .putData("type", "keyword")
                .putData("keyword", personalNotice.getKeyword().getName())
                .putData("title", personalNotice.getNotice().getTitle())
                .putData("content", personalNotice.getNotice().getContent().substring(0, 30) + "...")
                .putData("link", personalNotice.getNotice().getLink())
                .addAllTokens(fcmTokens)
                .build();
    }

    public MulticastMessage personalHomepageNoticeToMessage(PersonalNotice personalNotice, List<String> fcmTokens) {
        return MulticastMessage.builder()
                .putData("type", "homepage")
                .putData("homepage", personalNotice.getHomepage().getName())
                .putData("title", personalNotice.getNotice().getTitle())
                .putData("content", personalNotice.getNotice().getContent().substring(0, 30) + "...")
                .putData("link", personalNotice.getNotice().getLink())
                .addAllTokens(fcmTokens)
                .build();
    }

    public MulticastMessage toNotice(String title, String content, List<String> allUserTokens) {
        return MulticastMessage.builder()
                .putData("type", "all-notice")
                .putData("title", title)
                .putData("body", content)
                .addAllTokens(allUserTokens)
                .build();
    }
}
