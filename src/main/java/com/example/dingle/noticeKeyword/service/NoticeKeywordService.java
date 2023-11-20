package com.example.dingle.noticeKeyword.service;

import com.example.dingle.exception.BusinessLogicException;
import com.example.dingle.exception.ExceptionCode;
import com.example.dingle.keyword.entity.Keyword;
import com.example.dingle.notice.entity.Notice;
import com.example.dingle.noticeKeyword.entity.NoticeKeyword;
import com.example.dingle.noticeKeyword.repository.NoticeKeywordRepository;
import com.example.dingle.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NoticeKeywordService {
    private final NoticeKeywordRepository noticeKeywordRepository;

    // Create
    public NoticeKeyword createNoticeKeyword(NoticeKeyword noticeKeyword) {
        return noticeKeywordRepository.save(noticeKeyword);
    }

    public void createNoticeKeyword(Notice notice, List<Keyword> keywords) {
        for(Keyword keyword : keywords) {
            NoticeKeyword noticeKeyword = new NoticeKeyword();
            noticeKeyword.setKeyword(keyword);
            noticeKeyword.setNotice(notice);
            noticeKeywordRepository.save(noticeKeyword);
        }
    }

    // Read
    public NoticeKeyword findNoticeKeyword(long noticeKeywordId) {
        return verifiedNoticeKeyword(noticeKeywordId);
    }

    public List<NoticeKeyword> findNoticeKeywords(User user) {
        return noticeKeywordRepository.findByKeyword_UserKeywords_User(user);
    }

    // Update
    public NoticeKeyword updateNoticeKeyword(NoticeKeyword noticeKeyword) {
        NoticeKeyword findNoticeKeyword = verifiedNoticeKeyword(noticeKeyword.getId());

        Optional.ofNullable(noticeKeyword.getNotice()).ifPresent(findNoticeKeyword::setNotice);
        Optional.ofNullable(noticeKeyword.getKeyword()).ifPresent(findNoticeKeyword::setKeyword);

        return noticeKeywordRepository.save(findNoticeKeyword);
    }

    // Delete
    public void deleteNoticeKeyword(long noticeKeywordId) {
        NoticeKeyword noticeKeyword = verifiedNoticeKeyword(noticeKeywordId);
        noticeKeywordRepository.delete(noticeKeyword);
    }

    // 증명
    public NoticeKeyword verifiedNoticeKeyword(long noticeKeywordId) {
        Optional<NoticeKeyword> noticeKeyword = noticeKeywordRepository.findById(noticeKeywordId);
        return noticeKeyword.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOTICEKEYWORD_NOT_FOUND));
    }
}
