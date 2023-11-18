package com.example.dingle.notice.entity;

import com.example.dingle.auditable.Auditable;
import com.example.dingle.noticeKeyword.entity.NoticeKeyword;
import com.example.dingle.noticeMajor.entity.NoticeMajor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notice extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String title;

    @Column(length = 5000)
    private String content;

    @Column
    private Long pageNum;

    @Column
    private String image;

    @Column
    private String link;

    @OneToMany(mappedBy = "notice", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<NoticeMajor> noticeMajors = new ArrayList<>();

    @OneToMany(mappedBy = "notice", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<NoticeKeyword> noticeKeywords = new ArrayList<>();

    public Notice(String title, String content, String link) {
        this.title = title;
        this.content = content;
        this.link = link;
    }
}