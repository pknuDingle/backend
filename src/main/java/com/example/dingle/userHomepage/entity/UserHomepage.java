package com.example.dingle.userHomepage.entity;

import com.example.dingle.auditable.Auditable;
import com.example.dingle.homepage.entity.Homepage;
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
public class UserHomepage extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "HOMEPAGE_ID")
    private Homepage homepage;

    public UserHomepage(User user, Homepage homepage) {
        this.user = user;
        this.homepage = homepage;
    }
}
