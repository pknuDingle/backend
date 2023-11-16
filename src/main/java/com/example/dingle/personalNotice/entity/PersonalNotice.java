package com.example.dingle.personalNotice.entity;

import com.example.dingle.category.entity.Category;
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
    @JoinColumn(name = "category_id")
    private Category category;

    public PersonalNotice(User user, Notice notice, Category category) {
        this.user = user;
        this.notice = notice;
        this.category = category;
    }
}
