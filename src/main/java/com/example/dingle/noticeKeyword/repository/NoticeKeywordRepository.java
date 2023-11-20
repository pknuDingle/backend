package com.example.dingle.noticeKeyword.repository;

import com.example.dingle.keyword.entity.Keyword;
import com.example.dingle.notice.entity.Notice;
import com.example.dingle.noticeKeyword.entity.NoticeKeyword;
import com.example.dingle.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeKeywordRepository extends JpaRepository<NoticeKeyword, Long> {

    List<NoticeKeyword> findByKeywordId(long keywordId);

    List<NoticeKeyword> findByKeyword_UserKeywords_User(User user);
}
