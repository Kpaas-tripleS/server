package com.tripleS.server.user.controller;

import com.tripleS.server.global.dto.ResponseTemplate;
import com.tripleS.server.user.dto.request.SignUpRequest;
import com.tripleS.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseTemplate<?> signUp (@RequestBody SignUpRequest signUpRequest) {

        userService.signUp(signUpRequest);

        return ResponseTemplate.EMPTY_RESPONSE;
    }
}
