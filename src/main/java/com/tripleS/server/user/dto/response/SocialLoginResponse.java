package com.tripleS.server.user.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SocialLoginResponse(
        KakaoAccount kakaoAccount,
        Properties properties
) {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Properties(
            String nickname,
            String profileImage
    ) {
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public record Profile(
                String profileImage_Url
        ) {

        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record KakaoAccount(
            String email,
            String phoneNumber
    ) {
    }

    public String nickname() {
        return properties.nickname();
    }

    public String email() {
        return kakaoAccount.email();
    }

    public String profileImageUrl() {
        return properties.profileImage();
    }

    public String phoneNumber() {
        return kakaoAccount.phoneNumber();
    }
}