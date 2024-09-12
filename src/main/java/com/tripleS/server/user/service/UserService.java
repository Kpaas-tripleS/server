package com.tripleS.server.user.service;

import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.domain.type.LoginType;
import com.tripleS.server.user.domain.type.Role;
import com.tripleS.server.user.dto.request.LoginRequest;
import com.tripleS.server.user.dto.request.SignUpRequest;
import com.tripleS.server.user.dto.response.LoginResponse;
import com.tripleS.server.user.dto.response.FindUserResponse;
import com.tripleS.server.user.dto.response.GetUserInfoResponse;
import com.tripleS.server.user.dto.response.SocialLoginResponse;
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
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new UserNotFoundException(UserErrorCode.USER_NOT_FOUND);
        }

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);

        return LoginResponse.of(accessToken, refreshToken);
    }

    @Transactional
    public LoginResponse socialLogin(SocialLoginResponse socialUserInfo) {

        User user = userRepository.findByEmail(socialUserInfo.email())
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .nickname(socialUserInfo.nickname())
                            .email(socialUserInfo.email())
                            .profile_image(socialUserInfo.profileImageUrl())
                            .phone(socialUserInfo.phoneNumber())
                            .loginType(LoginType.KAKAO)
                            .role(Role.USER)
                            .build();
                    return userRepository.save(newUser);
                });

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);

        return LoginResponse.of(accessToken, refreshToken);
    }

    public FindUserResponse findUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

        return FindUserResponse.of(user);
    }

    public GetUserInfoResponse getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

        return GetUserInfoResponse.of(user);
    }

    @Transactional
    public void updateUserInfo(Long userId, GetUserInfoResponse getUserInfoResponse) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

        user.updateUserInfo(getUserInfoResponse);

        userRepository.save(user);
    }
}