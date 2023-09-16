package com.example.dingle.noticeCategory.repository;

import com.example.dingle.noticeCategory.entity.NoticeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeCategoryRepository extends JpaRepository<NoticeCategory, Long> {
}
