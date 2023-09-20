package com.example.dingle.noticeCategory.entity;

import com.example.dingle.auditable.Auditable;
import com.example.dingle.category.entity.Category;
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
public class NoticeCategory extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "NOTICE_ID")
    private Notice notice;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    public NoticeCategory(Notice notice, Category category) {
        this.notice = notice;
        this.category = category;
    }
}
