package com.tripleS.server.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginRequest(
        @JsonProperty("profile_nickname")
        String nickname,

        @JsonProperty("profile_email")
        String email,

        String password
) {
}
