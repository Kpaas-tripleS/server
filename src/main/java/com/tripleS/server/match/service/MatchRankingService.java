package com.tripleS.server.match.service;

import com.tripleS.server.friend.exception.FriendNotFoundException;
import com.tripleS.server.friend.exception.errorcode.FriendErrorCode;
import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.dto.response.UserResponse;
import com.tripleS.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchRankingService {

    private final UserRepository userRepository;

    public List<UserResponse> allRanking() {
        return userRepository.findUsersByWinCountDesc().stream()
                .map(UserResponse::from)
                .toList();
    }

    public List<UserResponse> friendRanking(Long userId) {
        User user = userRepository.findByIdFetchFriend(userId)
                .orElseThrow(() -> new FriendNotFoundException(FriendErrorCode.FRIEND_NOT_FOUND));
        return userRepository.findFriendsByWinCountDesc(userId).stream()
                .map(UserResponse::from)
                .toList();
    }
}
