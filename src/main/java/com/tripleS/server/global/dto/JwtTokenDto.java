package com.tripleS.server.global.dto;

public record JwtTokenDto(
        String accessToken,
        String refreshToken
) {
}
