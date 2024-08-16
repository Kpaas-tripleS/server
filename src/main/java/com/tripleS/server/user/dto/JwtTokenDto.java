package com.tripleS.server.user.dto;

//jwttokendto
public record JwtTokenDto(
        String accessToken,
        String refreshToken
) {
}
