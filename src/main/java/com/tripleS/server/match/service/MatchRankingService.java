package com.tripleS.server.match.service;

import com.tripleS.server.friend.exception.FriendNotFoundException;
import com.tripleS.server.friend.exception.errorcode.FriendErrorCode;
import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.dto.response.UserResponse;
import com.tripleS.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new FriendNotFoundException(FriendErrorCode.FRIEND_NOT_FOUND));
        List<User> friendsOfUser = userRepository.findFriendsOfUser(userId);
        List<User> friendsOfFriend = userRepository.findFriendsOfFriend(userId);
        List<User> allFriends = new ArrayList<>();
        allFriends.addAll(friendsOfUser);
        allFriends.addAll(friendsOfFriend);
        allFriends.sort(Comparator.comparingLong(User::getWin_count).reversed()
                .thenComparing(User::getNickname));
        return allFriends.stream()
                .map(UserResponse::from)
                .toList();
    }
}
