package com.example.dingle.notice.entity;

import com.example.dingle.auditable.Auditable;
import com.example.dingle.noticeCategory.entity.NoticeCategory;
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

    @Column(length = 1000)
    private String content;

    @Column
    private String image;

    @Column
    private String link;

    @OneToMany(mappedBy = "notice", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<NoticeCategory> noticeCategories = new ArrayList<>();
}