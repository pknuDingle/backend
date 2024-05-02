package com.example.dingle.keyword.entity;

import com.example.dingle.auditable.Auditable;
import com.example.dingle.noticeKeyword.entity.NoticeKeyword;
import com.example.dingle.user.entity.User;
import com.example.dingle.userKeyword.entity.UserKeyword;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Keyword extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column
    private CategoryType categoryType;

    @OneToMany(mappedBy = "keyword", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<NoticeKeyword> noticeKeywords = new ArrayList<>();

    @OneToMany(mappedBy = "keyword", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<UserKeyword> userKeywords = new ArrayList<>();

    public UserKeyword toUserKeyword(User user) {
        return new UserKeyword(user, this);
    }
}

