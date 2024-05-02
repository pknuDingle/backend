package com.example.dingle.notice.entity;

import com.example.dingle.auditable.Auditable;
import com.example.dingle.homepage.entity.Homepage;
import com.example.dingle.jjim.entity.Jjim;
import com.example.dingle.noticeKeyword.entity.NoticeKeyword;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Long noticeNum;

    @Column
    private String image;

    @Column
    private String link;

    @ManyToOne
    @JoinColumn(name = "HOMEPAGE_ID")
    private Homepage homepage;

    @OneToMany(mappedBy = "notice", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<NoticeKeyword> noticeKeywords = new ArrayList<>();

    @OneToMany(mappedBy = "notice", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Jjim> jjims = new ArrayList<>();

    public Notice(String title, String content, String link) {
        this.title = title;
        this.content = content;
        this.link = link;
    }

    public Notice(String title, String content, Long pageNum, Long noticeNum, String image,
            String link, Homepage homepage) {
        this.title = title;
        this.content = content;
        this.pageNum = pageNum;
        this.noticeNum = noticeNum;
        this.image = image;
        this.link = link;
        this.homepage = homepage;
    }

    public static Notice createWithPageNum(String title, String content, String link, String image,
            Long pageNum, Homepage homepage) {
        return new Notice(title, content, pageNum, null, image, link, homepage);
    }

    public static Notice createWithNoticeNum(String title, String content, String link,
            String image, Long noticeNum, Homepage homepage) {
        return new Notice(title, content, null, noticeNum, image, link, homepage);
    }
}