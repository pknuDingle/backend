package com.example.dingle.homepage.entity;

import com.example.dingle.auditable.Auditable;
import com.example.dingle.notice.entity.Notice;
import com.example.dingle.user.entity.User;
import com.example.dingle.userHomepage.entity.UserHomepage;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Homepage extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String url;

    @OneToMany(mappedBy = "homepage", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<UserHomepage> userHomepages = new ArrayList<>();

    @OneToMany(mappedBy = "homepage", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Notice> notices = new ArrayList<>();

    public UserHomepage toUserHomepage(User user) {
        return new UserHomepage(user, this);
    }
}
