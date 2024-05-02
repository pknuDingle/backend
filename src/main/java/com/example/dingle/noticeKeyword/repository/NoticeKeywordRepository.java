package com.example.dingle.noticeKeyword.repository;

import com.example.dingle.noticeKeyword.entity.NoticeKeyword;
import com.example.dingle.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeKeywordRepository extends JpaRepository<NoticeKeyword, Long> {

    List<NoticeKeyword> findByKeywordId(long keywordId);

    List<NoticeKeyword> findByKeyword_UserKeywords_User(User user);
}
