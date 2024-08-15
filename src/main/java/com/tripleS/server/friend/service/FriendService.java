package com.tripleS.server.friend.service;

import com.tripleS.server.friend.domain.Friend;
import com.tripleS.server.friend.dto.response.GetFriendsResponse;
import com.tripleS.server.friend.repository.FriendRepository;
import com.tripleS.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;

    public List<GetFriendsResponse> getFriends(Long userId) {

        List<Friend> friendsList =  friendRepository.findAllById(userId);

        return friendsList.stream()
                .map(friend -> GetFriendsResponse.from(friend.getUser()))
                .collect(Collectors.toList());
    }
}
