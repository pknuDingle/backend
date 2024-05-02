package com.example.dingle.user.entity;

import com.example.dingle.auditable.Auditable;
import com.example.dingle.jjim.entity.Jjim;
import com.example.dingle.userHomepage.entity.UserHomepage;
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
@Entity(name = "USERS")
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String imageUrl;

    @Column
    private String fcmToken;

    @Column
    private long kakakoId;

    @Enumerated(EnumType.STRING)
    private Attendance status = Attendance.DORMANT;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<UserHomepage> userHomepages = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<UserKeyword> userKeywords = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Jjim> jjims = new ArrayList<>();

    @Getter
    public enum Attendance {
        DORMANT("기숙사생"),
        NOT_DORMANT("기숙사생X");

        @Getter
        private final String state;

        Attendance(String state) {
            this.state = state;
        }
    }
}
