package com.example.dingle.noticeCategory.service;

import com.example.dingle.exception.BusinessLogicException;
import com.example.dingle.exception.ExceptionCode;
import com.example.dingle.noticeCategory.entity.NoticeCategory;
import com.example.dingle.noticeCategory.repository.NoticeCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class NoticeCategoryService {
    private final NoticeCategoryRepository noticeCategoryRepository;

    // Create
    public NoticeCategory createNoticeCategory(NoticeCategory noticeCategory) {
        return noticeCategoryRepository.save(noticeCategory);
    }

    // Read
    public NoticeCategory findNoticeCategory(long noticeCategoryId) {
        return verifiedNoticeCategory(noticeCategoryId);
    }

    // Update
    public NoticeCategory updateNoticeCategory(NoticeCategory noticeCategory) {
        NoticeCategory findNoticeCategory = verifiedNoticeCategory(noticeCategory.getId());

        Optional.ofNullable(noticeCategory.getNotice()).ifPresent(findNoticeCategory::setNotice);
        Optional.ofNullable(noticeCategory.getCategory()).ifPresent(findNoticeCategory::setCategory);

        return noticeCategoryRepository.save(findNoticeCategory);
    }

    // Delete
    public void deleteNoticeCategory(long noticeCategoryId) {
        NoticeCategory noticeCategory = verifiedNoticeCategory(noticeCategoryId);
        noticeCategoryRepository.delete(noticeCategory);
    }

    // 증명
    public NoticeCategory verifiedNoticeCategory(long noticeCategoryId) {
        Optional<NoticeCategory> noticeCategory = noticeCategoryRepository.findById(noticeCategoryId);
        return noticeCategory.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOTICECATEGORY_NOT_FOUND));
    }
}
