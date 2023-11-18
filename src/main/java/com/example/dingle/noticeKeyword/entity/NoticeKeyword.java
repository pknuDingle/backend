package com.example.dingle.noticeKeyword.entity;

import com.example.dingle.auditable.Auditable;
import com.example.dingle.keyword.entity.Keyword;
import com.example.dingle.notice.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class NoticeKeyword extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "NOTICE_ID")
    private Notice notice;

    @ManyToOne
    @JoinColumn(name = "KEYWORD_ID")
    private Keyword keyword;

    public NoticeKeyword(Notice notice, Keyword keyword) {
        this.notice = notice;
        this.keyword = keyword;
    }
}
