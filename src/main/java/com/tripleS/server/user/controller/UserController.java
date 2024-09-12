package com.tripleS.server.user.controller;

import com.tripleS.server.global.dto.AuthUser;
import com.tripleS.server.global.dto.ResponseTemplate;
import com.tripleS.server.user.dto.request.LoginRequest;
import com.tripleS.server.user.dto.request.SignUpRequest;
import com.tripleS.server.user.dto.response.FindUserResponse;
import com.tripleS.server.user.dto.response.GetUserInfoResponse;
import com.tripleS.server.user.dto.response.LoginResponse;
import com.tripleS.server.user.dto.response.SocialLoginResponse;
import com.tripleS.server.user.repository.UserRepository;
import com.tripleS.server.user.service.JwtTokenProvider;
import com.tripleS.server.user.service.SocialLoginService;
import com.tripleS.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final SocialLoginService socialLoginService;

    @PostMapping("/sign-up")
    public ResponseTemplate<?> signUp(@RequestBody SignUpRequest signUpRequest) {

        userService.signUp(signUpRequest);

        return ResponseTemplate.EMPTY_RESPONSE;
    }

    @PostMapping("/login")
    public ResponseTemplate<?> login(@RequestBody LoginRequest loginRequest) {

        LoginResponse loginResponse = userService.login(loginRequest);

        return ResponseTemplate.from(loginResponse);
    }

    @PostMapping("/kakao-login")
    public ResponseTemplate<?> socialLogin(@RequestParam("code") String code) {
        // 1. 코드로 액세스 토큰 가져오기
        String accessToken = socialLoginService.getAccessToken(code);

        // 2. 액세스 토큰으로 사용자 정보 가져오기
        SocialLoginResponse userInfo = socialLoginService.getSocialUserInfo(accessToken);

        // 3. 사용자 정보로 로그인 처리 (DB 저장 또는 업데이트)
        LoginResponse loginResponse = userService.socialLogin(userInfo);

        return ResponseTemplate.from(loginResponse);
    }


    @GetMapping("/test-token/{userId}")
    public String getTestToken(@PathVariable Long userId) {

        return jwtTokenProvider.createAccessToken(userRepository.findById(userId).orElseThrow());
    }

    @GetMapping("/friends")
    public ResponseEntity<ResponseTemplate<?>> findUser(@AuthenticationPrincipal AuthUser authUser,
                                                        @RequestParam Long friendId) {
        FindUserResponse user = userService.findUser(friendId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseTemplate.from(user));
    }

    @GetMapping("/profile")
    public ResponseEntity<ResponseTemplate<?>> getProfile(@AuthenticationPrincipal AuthUser authUser) {
        GetUserInfoResponse user = userService.getUserInfo(authUser.userId());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseTemplate.from(user));
    }

    @PutMapping("/profile")
    public ResponseEntity<ResponseTemplate<?>> updateProfile(@AuthenticationPrincipal AuthUser authUser,
                                                             @RequestBody GetUserInfoResponse userInfoResponse) {
        userService.updateUserInfo(authUser.userId(), userInfoResponse);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }
}
