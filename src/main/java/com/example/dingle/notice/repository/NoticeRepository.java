package com.example.dingle.notice.repository;

import com.example.dingle.homepage.entity.Homepage;
import com.example.dingle.notice.entity.Notice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findByHomepage(Homepage homepage);

    List<Notice> findByHomepageIn(List<Homepage> homepages);

    @Query("SELECT COALESCE(MAX(n.noticeNum), 0) FROM Notice n")
    Long findLatestNoticeNum();

    @Query("SELECT COALESCE(MAX(n.pageNum), 0) FROM Notice n")
    Long findLatestPageNum();
}