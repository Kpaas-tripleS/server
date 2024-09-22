package com.tripleS.server.user.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SocialLoginResponse(
        String id,
        KakaoAccount kakaoAccount,
        Properties properties
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Properties(
            String nickname,
            String thumbnailImage
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public record Profile(
                String thumbnailImageUrl
        ) {
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
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

    public String thumbnailImageUrl() {
        return properties.thumbnailImage();
    }

    public String phoneNumber() {
        return kakaoAccount.phoneNumber();
    }
}