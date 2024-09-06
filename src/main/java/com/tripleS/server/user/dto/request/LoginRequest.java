package com.tripleS.server.user.dto.request;

public record LoginRequest(
        String email,
        String password
) {
}
