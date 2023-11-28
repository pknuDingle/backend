package com.example.dingle.personalNotice.entity;

import com.example.dingle.homepage.entity.Homepage;
import com.example.dingle.keyword.entity.Keyword;
import com.example.dingle.notice.entity.Notice;
import com.example.dingle.user.entity.User;
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
public class PersonalNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "notice_id")
    private Notice notice;

    @OneToOne
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    @OneToOne
    @JoinColumn(name = "homepage_id")
    private Homepage homepage;

    public PersonalNotice(User user, Notice notice, Keyword keyword) {
        this.user = user;
        this.notice = notice;
        this.keyword = keyword;
    }

    public PersonalNotice(User user, Notice notice, Homepage homepage) {
        this.user = user;
        this.notice = notice;
        this.homepage = homepage;
    }
}
