package com.tripleS.server.user.controller;

import com.tripleS.server.global.dto.ResponseTemplate;
import com.tripleS.server.user.dto.request.LoginRequest;
import com.tripleS.server.user.dto.request.SignUpRequest;
import com.tripleS.server.user.dto.response.LoginResponse;
import com.tripleS.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/sign-up")
    public ResponseTemplate<?> signUp(@RequestBody SignUpRequest signUpRequest) {

        userService.signUp(signUpRequest);

        return ResponseTemplate.EMPTY_RESPONSE;
    }

    @PostMapping("/auth/login")
    public ResponseTemplate<?> login(@RequestBody LoginRequest loginRequest) {

        LoginResponse loginResponse = userService.login(loginRequest.email(), loginRequest.password());

        return ResponseTemplate.from(loginResponse);
    }
}
