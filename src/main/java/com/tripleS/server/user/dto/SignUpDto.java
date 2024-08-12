package com.tripleS.server.user.dto;

public record SignUpDto(
        String email,
        String password,
        String nickname
) {
}
