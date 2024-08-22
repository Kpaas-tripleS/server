package com.tripleS.server.friend.domain;

import com.tripleS.server.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BeFriend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_accepted")
    private boolean isAccepted = false;

    @Column(name = "nickname")
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Builder
    public BeFriend(User requester, User receiver, boolean isAccepted, String nickname) {
        this.requester = requester;
        this.receiver = receiver;
        this.isAccepted = isAccepted;
        this.nickname = nickname;
    }
}
