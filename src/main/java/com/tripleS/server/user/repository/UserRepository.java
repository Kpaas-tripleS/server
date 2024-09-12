package com.tripleS.server.user.repository;

import com.tripleS.server.user.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.win_count = u.win_count + 1 WHERE u.id = :userId")
    void updateWinCount(Long userId);

    @Query("SELECT u FROM User u ORDER BY u.win_count DESC, u.nickname ASC")
    List<User> findUsersByWinCountDesc();

    //@EntityGraph(attributePaths = {"friendList", "friendList.friend"})
    //@Query("SELECT u FROM User u WHERE u.id = :userId ORDER BY u.win_count DESC, u.nickname ASC")
    //Optional<User> findFriendsByWinCountDesc(Long userId);
    @Query("SELECT f.friend FROM Friend f WHERE f.user.id = :userId ORDER BY f.friend.win_count DESC, f.friend.nickname ASC")
    List<User> findFriendsByWinCountDesc(Long userId);

    Optional<User> findByEmail(String email);
    Optional<User> findById(Long userId);
  
}
