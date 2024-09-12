package com.tripleS.server.user.domain;

import com.tripleS.server.friend.domain.Friend;
import com.tripleS.server.user.domain.type.Grade;
import com.tripleS.server.user.domain.type.LoginType;
import com.tripleS.server.user.domain.type.Role;
import com.tripleS.server.user.dto.response.GetUserInfoResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email"}),
                @UniqueConstraint(columnNames = {"nickname"})
        },
        indexes = {
                @Index(columnList = "email"),
                @Index(columnList = "nickname")
        }
)
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "nickname", length = 20)
    private String nickname;

    @Column(name = "grade")
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Column(name = "win_count")
    private Long win_count;

    @Column(name = "login_type")
    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Column(name = "email")
    private String email;

    @Column(name = "local_pw")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "profile_image")
    private String profileImage;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friend> friendList = new ArrayList<>();

    @Builder
    public User(String name, String phone, String nickname, Grade grade, Long win_count,
                LoginType loginType, String email, String password, Role role, String profile_image, String refreshToken) {
        this.name = name;
        this.phone = phone;
        this.nickname = nickname;
        this.grade = grade;
        this.win_count = win_count;
        this.loginType = loginType;
        this.email = email;
        this.password = password;
        this.role = role;
        this.profileImage = profile_image;
    }

    public void updateUserInfo(GetUserInfoResponse getUserInfoResponse) {
        this.nickname = getUserInfoResponse.nickname();
        this.password = getUserInfoResponse.password();
        this.profileImage = getUserInfoResponse.profileImage();
        this.phone = getUserInfoResponse.phoneNumber();
    }
}