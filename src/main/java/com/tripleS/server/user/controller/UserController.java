package com.tripleS.server.user.controller;

import com.tripleS.server.global.dto.ResponseTemplate;
import com.tripleS.server.user.dto.request.LoginRequest;
import com.tripleS.server.user.dto.request.SignUpRequest;
import com.tripleS.server.user.dto.response.LoginResponse;
import com.tripleS.server.user.repository.UserRepository;
import com.tripleS.server.user.service.JwtTokenProvider;
import com.tripleS.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @PostMapping("/sign-up")
    public ResponseTemplate<?> signUp(@RequestBody SignUpRequest signUpRequest) {

        userService.signUp(signUpRequest);

        return ResponseTemplate.EMPTY_RESPONSE;
    }

    @PostMapping("/login")
    public ResponseTemplate<?> login(@RequestBody LoginRequest loginRequest) {

        LoginResponse loginResponse = userService.login(loginRequest.email(), loginRequest.password());

        return ResponseTemplate.from(loginResponse);
    }

    @GetMapping("/test-token/{userId}")
    public String getTestToken(@PathVariable Long userId) {

        return jwtTokenProvider.createAccessToken(userRepository.findById(userId).orElseThrow());
    }
}
