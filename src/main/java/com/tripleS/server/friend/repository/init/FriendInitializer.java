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
public class FriendInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (friendRepository.count() != 0) {
            log.info("Friend data already exists.");
        } else {
            User user = userRepository.findById(1L).orElseThrow();
            User friend = userRepository.findById(2L).orElseThrow();

            List<Friend> friendList = new ArrayList<>();

            Friend friend1 = Friend.builder()
                    .user(user)
                    .friend(friend)
                    .build();
            friendList.add(friend1);

            friendRepository.saveAll(friendList);
        }
    }
}
