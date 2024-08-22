package com.tripleS.server.friend.repository;

import com.tripleS.server.friend.domain.BeFriend;
import com.tripleS.server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeFriendRepository extends JpaRepository<BeFriend, Long> {
    List<BeFriend> findByReceiver(User receiver);
}
