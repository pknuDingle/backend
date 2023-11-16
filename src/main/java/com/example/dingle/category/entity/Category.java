package com.example.dingle.category.entity;

import com.example.dingle.auditable.Auditable;
import com.example.dingle.noticeCategory.entity.NoticeCategory;
import com.example.dingle.user.entity.User;
import com.example.dingle.userCategory.entity.UserCategory;
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
public class Category extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column
    private CategoryType categoryType;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<NoticeCategory> noticeCategories = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<UserCategory> userCategories = new ArrayList<>();

    public UserCategory toUserCategory(User user) {
        return new UserCategory(user, this);
    }
}

