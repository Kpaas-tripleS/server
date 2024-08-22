package com.tripleS.server.user.service;

import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.dto.request.SignUpRequest;
import com.tripleS.server.user.dto.response.LoginResponse;
import com.tripleS.server.user.exception.EmailDuplicatedException;
import com.tripleS.server.user.exception.NicknameDuplicatedException;
import com.tripleS.server.user.exception.UserNotFoundException;
import com.tripleS.server.user.exception.errorcode.UserErrorCode;
import com.tripleS.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void signUp(SignUpRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.email())) {
            throw new EmailDuplicatedException(UserErrorCode.EMAIL_DUPLICATED);
        }
        if (userRepository.existsByNickname(signUpRequest.nickname())) {
            throw new NicknameDuplicatedException(UserErrorCode.NICKNAME_DUPLICATED);
        }

        userRepository.save(signUpRequest.toEntity(passwordEncoder));
    }

    @Transactional
    public LoginResponse login(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);

        return LoginResponse.of(accessToken, refreshToken);
    }
}