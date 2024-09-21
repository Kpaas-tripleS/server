package com.tripleS.server.match.repository;

import com.tripleS.server.match.domain.Match;
import com.tripleS.server.match.domain.type.MatchType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("SELECT m FROM Match m WHERE m.isMatch = false AND m.matchType = :matchType")
    List<Match> findByIsMatchFalseAndMatchTypeRandom(@Param("matchType") MatchType matchType);

    @Query("SELECT m FROM Match m WHERE m.matchType = :matchType AND m.follower.id = :userId AND m.isMatch = false")
    List<Match> findFriendMatch(@Param("matchType") MatchType matchType, @Param("userId") Long userId);

}
