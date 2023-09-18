package com.example.dingle.user.entity;

import com.example.dingle.auditable.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    private String email;

    @Column
    private String image;

    @Column
    private String token;


    @Enumerated(EnumType.STRING)
    private Attendance status = Attendance.DORMANT;

    @Getter
    public static enum Attendance {
        DORMANT("기숙사생"),
        NOT_DORMANT("기숙사생X");

        @Getter
        private String state;

        Attendance(String state) {
            this.state = state;
        }
    }
}
