package com.tripleS.server.user.repository.init;

import com.tripleS.server.global.util.DummyDataInit;
import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.domain.type.Grade;
import com.tripleS.server.user.domain.type.LoginType;
import com.tripleS.server.user.domain.type.Role;
import com.tripleS.server.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Order(1)
@RequiredArgsConstructor
@Transactional
@DummyDataInit
public class UserInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() != 0) {
            log.info("User data already exists.");
        } else {
            List<User> userList = new ArrayList<>();

            User DUMMY_USER1 = User.builder()
                    .name("user")
                    .email("user1")
                    .grade(Grade.THREE)
                    .loginType(LoginType.LOCAL)
                    .nickname("user1")
                    .phone("010-1111-1111")
                    .password(passwordEncoder.encode("1234"))
                    .role(Role.ADMIN)
                    .win_count(0L)
                    .profile_image(null)
                    .build();
            User DUMMY_USER2 = User.builder()
                    .name("user2")
                    .email("user2")
                    .grade(Grade.THREE)
                    .loginType(LoginType.LOCAL)
                    .nickname("user2")
                    .phone("010-2222-2222")
                    .password(passwordEncoder.encode("1234"))
                    .role(Role.ADMIN)
                    .win_count(0L)
                    .profile_image(null)
                    .build();
            User DUMMY_USER3 = User.builder()
                    .name("user3")
                    .email("user3")
                    .grade(Grade.THREE)
                    .loginType(LoginType.LOCAL)
                    .nickname("user3")
                    .phone("010-3333-3333")
                    .password(passwordEncoder.encode("1234"))
                    .role(Role.ADMIN)
                    .win_count(0L)
                    .profile_image(null)
                    .build();
            User DUMMY_USER4 = User.builder()
                    .name("user4")
                    .email("user4")
                    .grade(Grade.THREE)
                    .loginType(LoginType.LOCAL)
                    .nickname("user4")
                    .phone("010-4444-4444")
                    .password(passwordEncoder.encode("1234"))
                    .role(Role.ADMIN)
                    .win_count(0L)
                    .profile_image(null)
                    .build();
            User DUMMY_USER5 = User.builder()
                    .name("user5")
                    .email("user5")
                    .grade(Grade.THREE)
                    .loginType(LoginType.LOCAL)
                    .nickname("user5")
                    .phone("010-5555-5555")
                    .password(passwordEncoder.encode("1234"))
                    .role(Role.ADMIN)
                    .win_count(0L)
                    .profile_image(null)
                    .build();
            User DUMMY_USER6 = User.builder()
                    .name("user6")
                    .email("user6")
                    .grade(Grade.THREE)
                    .loginType(LoginType.LOCAL)
                    .nickname("user6")
                    .phone("010-6666-6666")
                    .password(passwordEncoder.encode("1234"))
                    .role(Role.ADMIN)
                    .win_count(0L)
                    .profile_image(null)
                    .build();

            userList.add(DUMMY_USER1);
            userList.add(DUMMY_USER2);
            userList.add(DUMMY_USER3);
            userList.add(DUMMY_USER4);
            userList.add(DUMMY_USER5);
            userList.add(DUMMY_USER6);

            userRepository.saveAll(userList);
        }
    }
}
