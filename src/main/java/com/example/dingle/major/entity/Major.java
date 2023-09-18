package com.example.dingle.major.entity;

import com.example.dingle.auditable.Auditable;
import com.example.dingle.noticeMajor.entity.NoticeMajor;
import com.example.dingle.userMajor.entity.UserMajor;
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
public class Major extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "major", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<UserMajor> userMajors = new ArrayList<>();

    @OneToMany(mappedBy = "major", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<NoticeMajor> noticeMajors = new ArrayList<>();
}
