package com.example.dingle.notice.service;

import com.example.dingle.exception.BusinessLogicException;
import com.example.dingle.exception.ExceptionCode;
import com.example.dingle.homepage.entity.Homepage;
import com.example.dingle.jjim.entity.Jjim;
import com.example.dingle.jjim.repository.JjimRepository;
import com.example.dingle.notice.entity.Notice;
import com.example.dingle.notice.repository.NoticeRepository;
import com.example.dingle.noticeKeyword.entity.NoticeKeyword;
import com.example.dingle.noticeKeyword.service.NoticeKeywordService;
import com.example.dingle.user.entity.User;
import com.example.dingle.userHomepage.service.UserHomepageService;
import com.example.dingle.userKeyword.service.UserKeywordService;
import com.example.dingle.util.FindUserByJWT;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final FindUserByJWT findUserByJWT;
    private final JjimRepository jjimRepository;
    private final UserHomepageService userHomepageService;
    private final UserKeywordService userKeywordService;
    private final NoticeKeywordService noticeKeywordService;

    // Create
    public Notice createNotice(Notice notice) {
        // 크롤링 테스트
//        PknuMain pknuMain = new PknuMain();
//        notice = pknuMain.crawling(712274);

        return noticeRepository.save(notice);
    }

    // Read
    public Notice findNotice(long noticeId) {
        return verifiedNotice(noticeId);
    }

    public List<Notice> findNoticeByKeyword() {
        User user = findUserByJWT.getLoginUser();
        List<NoticeKeyword> noticeKeywords = noticeKeywordService.findNoticeKeywords(user);
        return noticeKeywords.stream().map(NoticeKeyword::getNotice).collect(Collectors.toList());
    }

    public List<Notice> findNoticeByHomepage() {
        User user = findUserByJWT.getLoginUser();
        List<Homepage> homepages = userHomepageService.findHomepageByUser(user);
        return noticeRepository.findByHomepageIn(homepages);
    }

    // Update
    public Notice updateNotice(Notice notice) {
        Notice findNotice = verifiedNotice(notice.getId());

        Optional.ofNullable(notice.getTitle()).ifPresent(findNotice::setTitle);
        Optional.ofNullable(notice.getContent()).ifPresent(findNotice::setContent);
        Optional.ofNullable(notice.getImage()).ifPresent(findNotice::setImage);
        Optional.ofNullable(notice.getLink()).ifPresent(findNotice::setLink);

        return noticeRepository.save(findNotice);
    }

    // Delete
    public void deleteNotice(long noticeId) {
        Notice notice = verifiedNotice(noticeId);
        noticeRepository.delete(notice);
    }

    // 증명
    public Notice verifiedNotice(long noticeId) {
        Optional<Notice> notice = noticeRepository.findById(noticeId);
        return notice.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOTICE_NOT_FOUND));
    }

    public boolean isFavorite(Notice notice) {
        User user = findUserByJWT.getLoginUser();
        Optional<Jjim> optionalJjim = jjimRepository.findByUserAndNotice(user, notice);
        Jjim jjim = optionalJjim.orElse(null);

        if(jjim == null) return false;
        return true;
    }

    public List<Boolean> isFavorite(List<Notice> notices) {
        return notices.stream().map(this::isFavorite).collect(Collectors.toList());
    }
}
