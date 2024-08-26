package com.tripleS.server.match.repository;

import com.tripleS.server.match.domain.Match;
import com.tripleS.server.match.domain.MatchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MatchResultRepository extends JpaRepository<MatchResult, Long>  {

    @Modifying
    @Transactional
    @Query("UPDATE MatchResult mr SET mr.leaderScore = mr.leaderScore + 1 WHERE mr.match.id = :matchId")
    void updateLeaderScore(Long matchId);

    @Modifying
    @Transactional
    @Query("UPDATE MatchResult mr SET mr.followerScore = mr.followerScore + 1 WHERE mr.match.id = :matchId")
    void updateFollowerScore(Long matchId);

    @Query("SELECT mr FROM MatchResult mr WHERE mr.match.id = :matchId")
    MatchResult findByMatchId(Long matchId);

}
