package com.tripleS.server.user.dto;

public record JwtTokenDto(
        String accessToken,
        String refreshToken
) {
}
