package com.example.dingle.userCategory.entity;

import com.example.dingle.auditable.Auditable;
import com.example.dingle.category.entity.Category;
import com.example.dingle.major.entity.Major;
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
public class UserCategory extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    public UserCategory(User user, Category category) {
        this.user = user;
        this.category = category;
    }

    public PersonalNotice toPersonalNotice(Notice notice){
        return new PersonalNotice(user, notice, category);
    }
}
