package com.tripleS.server.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SocialLoginResponse(
        @JsonProperty("profile_name")
        String nickname,

        @JsonProperty("profile_email")
        String email,

        @JsonProperty("profile_image")
        String profileImageUrl,

        @JsonProperty("profile_phone")
        String phoneNumber
){
}