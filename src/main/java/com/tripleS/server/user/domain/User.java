package com.tripleS.server.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "grade")
    private String grade;

    @Column(name = "win")
    private Long win;

    @Column(name = "login_type")
    private String loginType;
}
