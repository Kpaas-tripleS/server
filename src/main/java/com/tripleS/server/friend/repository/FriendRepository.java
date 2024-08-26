package com.tripleS.server.friend.repository;

import com.tripleS.server.friend.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    Optional<Friend> findByUserIdAndFriendId(Long userId, Long friendId);
    List<Friend> findByUserIdAndIsAcceptedFalse(Long userId);
}
