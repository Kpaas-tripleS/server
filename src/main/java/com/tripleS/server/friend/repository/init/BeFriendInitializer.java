package com.tripleS.server.friend.repository.init;

import com.tripleS.server.friend.domain.BeFriend;
import com.tripleS.server.friend.repository.BeFriendRepository;
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
    private final BeFriendRepository beFriendRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (beFriendRepository.count() != 0) {
            log.info("BeFriend data already exists.");
        } else {
            User USER1 = userRepository.findById(1L).orElseThrow();
            User USER2 = userRepository.findById(2L).orElseThrow();
            User USER3 = userRepository.findById(3L).orElseThrow();
            User USER4 = userRepository.findById(4L).orElseThrow();
            User USER5 = userRepository.findById(5L).orElseThrow();
            User USER6 = userRepository.findById(6L).orElseThrow();

            List<BeFriend> friendList = new ArrayList<>();

            BeFriend beFriend1 = BeFriend.builder()
                    .requester(USER2)
                    .receiver(USER1)
                    .build();

            BeFriend beFriend2 = BeFriend.builder()
                    .requester(USER3)
                    .receiver(USER1)
                    .build();

            BeFriend beFriend3 = BeFriend.builder()
                    .requester(USER4)
                    .receiver(USER1)
                    .build();

            BeFriend beFriend4 = BeFriend.builder()
                    .requester(USER5)
                    .receiver(USER1)
                    .build();

            BeFriend beFriend5 = BeFriend.builder()
                    .requester(USER6)
                    .receiver(USER1)
                    .build();

            friendList.add(beFriend1);
            friendList.add(beFriend2);
            friendList.add(beFriend3);
            friendList.add(beFriend4);
            friendList.add(beFriend5);

            beFriendRepository.saveAll(friendList);
        }
    }
}
