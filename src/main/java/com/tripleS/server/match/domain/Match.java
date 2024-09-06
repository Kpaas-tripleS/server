package com.tripleS.server.match.domain;

import com.tripleS.server.match.domain.type.MatchType;
import com.tripleS.server.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "`match`")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_match", nullable = false)
    private Boolean isMatch;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @ManyToOne
    @JoinColumn(name = "follower", referencedColumnName = "id")
    private User follower;

    @ManyToOne(optional = false)
    @JoinColumn(name = "leader", referencedColumnName = "id")
    private User leader;

    @Column(name = "match_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MatchType matchType;

    @Builder
    public Match(Boolean isMatch, LocalDateTime createTime, User follower, User leader, MatchType matchType) {
        this.isMatch = isMatch;
        this.createTime = createTime;
        this.follower = follower;
        this.leader = leader;
        this.matchType = matchType;
    }

}

