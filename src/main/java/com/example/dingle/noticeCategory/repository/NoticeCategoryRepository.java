package com.example.dingle.noticeCategory.repository;

import com.example.dingle.noticeCategory.entity.NoticeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeCategoryRepository extends JpaRepository<NoticeCategory, Long> {

    List<NoticeCategory> findByCategory_Id(long categoryId);
}
