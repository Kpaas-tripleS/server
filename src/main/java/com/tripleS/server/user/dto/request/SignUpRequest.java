package com.tripleS.server.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.domain.type.Grade;
import com.tripleS.server.user.domain.type.LoginType;
import com.tripleS.server.user.domain.type.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

public record SignUpRequest(
        String name,

        String email,

        String password,

        String nickname,

        String phone
) {
    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .name(name)
                .phone(phone)
                .nickname(nickname)
                .grade(Grade.ONE)
                .win_count(0L)
                .loginType(LoginType.LOCAL)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .build();
    }
}
