package com.tripleS.server.user.controller;

import com.nimbusds.oauth2.sdk.AccessTokenResponse;
import com.tripleS.server.user.dto.request.AccessTokenRequest;
import com.tripleS.server.user.dto.response.SocialLoginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoClient", url = "https://kapi.kakao.com")
public interface KakaoClient {

        @PostMapping("/oauth/token") // 카카오톡 서버에서 액세스 토큰을 가져오는 API
        AccessTokenResponse getAccessToken(@RequestBody AccessTokenRequest accessTokenRequest);

        @GetMapping("/v2/user/me")      //카카오톡 서버에서 사용자 정보가져오는 api
        SocialLoginResponse getUserInfo(@RequestParam("access_token") String accessToken);
}
