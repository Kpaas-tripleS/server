package com.tripleS.server.user.repository.init;

import com.tripleS.server.global.util.DummyDataInit;
import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.domain.type.Grade;
import com.tripleS.server.user.domain.type.LoginType;
import com.tripleS.server.user.domain.type.Role;
import com.tripleS.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

@Slf4j
@Order(1)
@RequiredArgsConstructor
@DummyDataInit
public class UserInitializer implements ApplicationRunner {

    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() != 0) {
            log.info("User data already exists.");
        } else {
            User DUMMY_USER1 = User.builder()
                    .name("admin")
                    .email("asd")
                    .grade(Grade.THREE)
                    .loginType(LoginType.KAKAO)
                    .nickname("admin")
                    .phone("010-1234-5678")
                    .password("1234")
                    .role(Role.ADMIN)
                    .win_count(0L)
                    .profile_image(null)
                    .build();
            User DUMMY_USER2 = User.builder()
                    .name("admin")
                    .email("asd")
                    .grade(Grade.THREE)
                    .loginType(LoginType.KAKAO)
                    .nickname("admin")
                    .phone("010-1234-5678")
                    .password("1234")
                    .role(Role.ADMIN)
                    .win_count(0L)
                    .profile_image(null)
                    .build();

            userRepository.save(DUMMY_USER1);
            userRepository.save(DUMMY_USER2);
        }
    }
}
