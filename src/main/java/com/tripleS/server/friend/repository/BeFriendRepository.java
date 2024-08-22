package com.tripleS.server.friend.repository;

import com.tripleS.server.friend.domain.BeFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeFriendRepository extends JpaRepository<BeFriend, Long> {

}
