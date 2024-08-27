package com.tripleS.server.user.repository;

import com.tripleS.server.user.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    @EntityGraph(attributePaths = {"friendList", "friendList.friend"})
//    @Query("SELECT u FROM User u WHERE u.id = :userId")
//    Optional<User> findByIdFetchFriend(Long userId);
//
//    @Query("SELECT u FROM User u JOIN u.friendList f " +
//            "WHERE f.friend.id = ?1 AND f.isAccepted = false")
//    Optional<User> findByFriend(Long userId);
//
    @EntityGraph(attributePaths = "friendList")
    @Query("SELECT u FROM User u LEFT JOIN u.friendList f WHERE u.id = :userId AND f.isAccepted = true")
    Optional<User> findByIdWithFriends(Long userId);


    Boolean existsByEmail(String email);
    Boolean existsByNickname(String nickname);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long userId);
}
