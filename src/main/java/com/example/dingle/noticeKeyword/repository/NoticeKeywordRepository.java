package com.example.dingle.noticeKeyword.repository;

import com.example.dingle.noticeKeyword.entity.NoticeKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeKeywordRepository extends JpaRepository<NoticeKeyword, Long> {

    List<NoticeKeyword> findByKeywordId(long keywordId);
}
