package com.example.dingle.noticeMajor.entity;

import com.example.dingle.auditable.Auditable;
import com.example.dingle.major.entity.Major;
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
public class NoticeMajor extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "MAJOR_ID")
    private Major major;

    @ManyToOne
    @JoinColumn(name = "NOTICE_ID")
    private Notice notice;
}
