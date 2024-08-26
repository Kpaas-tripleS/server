package com.tripleS.server.match.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "leaderScore", nullable = false)
    private Long leaderScore;

    @Column(name = "followerScore", nullable = false)
    private Long followerScore;

    @OneToOne(optional = false)
    @JoinColumn(name = "matchId", referencedColumnName = "id")
    private Match match;

    @Builder
    public MatchResult(Long leaderScore, Long followerScore, Match match) {
        this.leaderScore = leaderScore;
        this.followerScore = followerScore;
        this.match = match;
    }
}
