package com.example.dingle.userKeyword.entity;

import com.example.dingle.auditable.Auditable;
import com.example.dingle.keyword.entity.Keyword;
import com.example.dingle.notice.entity.Notice;
import com.example.dingle.personalNotice.entity.PersonalNotice;
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
public class UserKeyword extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "KEYWORD_ID")
    private Keyword keyword;

    public UserKeyword(User user, Keyword keyword) {
        this.user = user;
        this.keyword = keyword;
    }

    public PersonalNotice toPersonalNotice(Notice notice) {
        return new PersonalNotice(user, notice, keyword);
    }
}
