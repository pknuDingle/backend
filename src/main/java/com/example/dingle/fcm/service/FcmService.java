package com.example.dingle.fcm.service;

import com.example.dingle.fcm.config.FirebaseMessagingWrapper;
import com.example.dingle.fcm.converter.FcmMessageConverter;
import com.example.dingle.personalNotice.entity.PersonalNotice;
import com.example.dingle.user.repository.UserRepository;
import com.google.firebase.messaging.MulticastMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FcmService {

    private final FcmMessageConverter fcmMessageConverter;
    private final FirebaseMessagingWrapper firebaseMessagingWrapper;
    private final UserRepository userRepository;

    /**
     * 사용자가 설정한 키워드 공지사항 알림
     */
    public void sendPersonalKeywordNoticeToUser(List<PersonalNotice> personalNotices) throws Exception {
        if (!personalNotices.isEmpty()) {
            List<String> fcmTokens = personalNotices.stream().map(personalNotice -> personalNotice.getUser().getFcmToken()).collect(Collectors.toList());

            MulticastMessage message = fcmMessageConverter.personalKeywordNoticeToMessage(personalNotices.get(0), fcmTokens);
            firebaseMessagingWrapper.sendMulticast(message);
        }
    }

    /**
     * 사용자가 설정한 홈페이지 공지사항 알림
     */
    public void sendPersonalHomepageNoticeToUser(List<PersonalNotice> personalNotices) throws Exception {
        if (!personalNotices.isEmpty()) {
            List<String> fcmTokens = personalNotices.stream().map(personalNotice -> personalNotice.getUser().getFcmToken()).collect(Collectors.toList());

            MulticastMessage message = fcmMessageConverter.personalHomepageNoticeToMessage(personalNotices.get(0), fcmTokens);
            firebaseMessagingWrapper.sendMulticast(message);
        }
    }

    /**
     * 전체 공지사항 알림
     */
    public void sendNoticeToAll(String title, String content) throws Exception {
        List<String> allUserTokens = userRepository.findAll().stream().map(user -> user.getFcmToken()).collect(Collectors.toList());
        MulticastMessage message = fcmMessageConverter.toNotice(title, content, allUserTokens);
        firebaseMessagingWrapper.sendMulticast(message);
    }
}