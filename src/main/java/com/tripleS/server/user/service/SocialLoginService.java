package com.tripleS.server.user.service;

import com.nimbusds.oauth2.sdk.AccessTokenResponse;
import com.tripleS.server.user.controller.KakaoClient;
import com.tripleS.server.user.dto.request.AccessTokenRequest;
import com.tripleS.server.user.dto.response.SocialLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SocialLoginService {

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    private final KakaoClient kakaoClient;

    public String getAccessToken(String code) {
        AccessTokenRequest request = new AccessTokenRequest(
                "authorization_code", // grant_type
                clientId, // client_id
                redirectUri, // redirect_uri
                code // authorization code
        );

        AccessTokenResponse accessTokenResponse = kakaoClient.getAccessToken(request);
        return accessTokenResponse.getTokens().getAccessToken().getValue();
    }

    public SocialLoginResponse getSocialUserInfo(String accessToken) {
        return kakaoClient.getUserInfo(accessToken);
    }
}
