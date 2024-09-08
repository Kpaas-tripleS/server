package com.tripleS.server.user.dto.request;

public record AccessTokenRequest(
        String grantType,
        String clientId,
        String redirectUri,
        String code
) {
}
