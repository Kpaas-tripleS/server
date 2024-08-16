package com.tripleS.server.room.domain;

import com.tripleS.server.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//room
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

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

    @Builder
    public Room(Boolean isMatch, LocalDateTime createTime, User follower, User leader) {
        this.isMatch = isMatch;
        this.createTime = createTime;
        this.follower = follower;
        this.leader = leader;
    }

    public Long getId() {
        return id;
    }

    public void setIsMatch(Boolean match) {
        isMatch = match;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }
}

