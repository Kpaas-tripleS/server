package com.tripleS.server.user.dto.request;

public record LoginRequest(
        String nickname,
        String email,
        String password
) {
}
