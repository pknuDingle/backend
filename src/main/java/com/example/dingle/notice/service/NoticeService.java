package com.example.dingle.notice.service;

import com.example.dingle.exception.BusinessLogicException;
import com.example.dingle.exception.ExceptionCode;
import com.example.dingle.notice.entity.Notice;
import com.example.dingle.notice.repository.NoticeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    // Create
    public Notice createNotice(Notice notice) {
        return noticeRepository.save(notice);
    }

    // Read
    public Notice findNotice(long noticeId) {
        return verifiedNotice(noticeId);
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
}
