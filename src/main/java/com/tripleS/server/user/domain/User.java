package com.tripleS.server.user.domain;

import com.tripleS.server.user.domain.type.Grade;
import com.tripleS.server.user.domain.type.LoginType;
import com.tripleS.server.user.domain.type.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    @Column(name = "nickname", length = 20, nullable = false)
    private String nickname;

    @Column(name = "grade", nullable = false)
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Column(name = "win_count", nullable = false)
    private Long win_count;

    @Column(name = "login_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "local_pw")
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String name, String phone, String nickname, Grade grade, Long win_count,
                LoginType loginType, String email, String password, Role role) {
        this.name = name;
        this.phone = phone;
        this.nickname = nickname;
        this.grade = grade;
        this.win_count = win_count;
        this.loginType = loginType;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
