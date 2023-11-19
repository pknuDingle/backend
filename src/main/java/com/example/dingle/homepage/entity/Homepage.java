package com.example.dingle.homepage.entity;

import com.example.dingle.auditable.Auditable;
import com.example.dingle.user.entity.User;
import com.example.dingle.userHomepage.entity.UserHomepage;
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

    public UserHomepage toUserHomepage(User user) {
        return new UserHomepage(user, this);
    }
}
