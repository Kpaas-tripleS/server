package com.tripleS.server.user.dto.request;

import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.domain.type.Grade;
import com.tripleS.server.user.domain.type.LoginType;
import com.tripleS.server.user.domain.type.Role;

//signuprequest
public record SignUpRequest(
        String name,
        String email,
        String password,
        String nickname,
        String phone
) {
    public User toEntity() {
        return User.builder()
                .name(name)
                .phone(phone)
                .nickname(nickname)
                .grade(Grade.ONE)
                .win_count(0L)
                .loginType(LoginType.LOCAL)
                .email(email)
                .password(password)
                .role(Role.USER)
                .build();
    }
}
