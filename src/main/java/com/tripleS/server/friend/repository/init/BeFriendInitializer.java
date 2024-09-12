package com.tripleS.server.friend.repository.init;

import com.tripleS.server.friend.domain.Friend;
import com.tripleS.server.friend.repository.FriendRepository;
import com.tripleS.server.global.util.DummyDataInit;
import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Order(2)
@RequiredArgsConstructor
@DummyDataInit
public class BeFriendInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (friendRepository.count() != 0) {
            log.info("BeFriend data already exists.");
        } else {
            User USER1 = userRepository.findById(1L).orElseThrow();
            User USER2 = userRepository.findById(2L).orElseThrow();
            User USER3 = userRepository.findById(3L).orElseThrow();
            User USER4 = userRepository.findById(4L).orElseThrow();
            User USER5 = userRepository.findById(5L).orElseThrow();
            User USER6 = userRepository.findById(6L).orElseThrow();

            List<Friend> friendList = new ArrayList<>();

            Friend beFriend1 = Friend.builder()
                    .user(USER1)
                    .friend(USER2)
                    .isAccepted(true)
                    .build();
            Friend beFriend2 = Friend.builder()
                    .user(USER1)
                    .friend(USER3)
                    .isAccepted(true)
                    .build();

            Friend beFriend3 = Friend.builder()
                    .user(USER1)
                    .friend(USER4)
                    .isAccepted(false)
                    .build();
            Friend beFriend4 = Friend.builder()
                    .user(USER1)
                    .friend(USER5)
                    .isAccepted(false)
                    .build();
            Friend beFriend5 = Friend.builder()
                    .user(USER1)
                    .friend(USER6)
                    .isAccepted(false)
                    .build();

            friendList.add(beFriend1);
            friendList.add(beFriend2);
            friendList.add(beFriend3);
            friendList.add(beFriend4);
            friendList.add(beFriend5);

            friendRepository.saveAll(friendList);
        }
    }
}
