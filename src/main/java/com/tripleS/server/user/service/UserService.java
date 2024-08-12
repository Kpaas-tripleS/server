package com.tripleS.server.user.service;

import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.dto.SignUpDto;
import com.tripleS.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void signUp (SignUpDto signUpDto) {
        userRepository.findByEmail(signUpDto.email())
                .orElseThrow(() -> new RuntimeException("이미 존재하는 이메일입니다."));

        userRepository.findByNickname(signUpDto.nickname())
                .orElseThrow(() -> new RuntimeException("이미 존재하는 닉네임입니다."));

        User user = User.builder()
                .email(signUpDto.email())
                .password(signUpDto.password()) //비밀번호 암호화 필요함
                .nickname(signUpDto.nickname())
                .build();

        userRepository.save(user);
    }
}
