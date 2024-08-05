package com.tripleS.server.user.domain.type;

import com.tripleS.server.user.domain.User;
import jakarta.persistence.*;

@Entity
public class SnsAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long snsAuthId;

    @Column(name = "sns_id")
    private String snsId;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userId;
}
