package com.example.dingle.notice.repository;

import com.example.dingle.homepage.entity.Homepage;
import com.example.dingle.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByHomepage(Homepage homepage);

    @Query("SELECT COALESCE(MAX(n.noticeNum), 0) FROM Notice n")
    Long findLatestNoticeNum();
}
