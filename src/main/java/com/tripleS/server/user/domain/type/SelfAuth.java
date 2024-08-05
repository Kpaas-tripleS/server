package com.tripleS.server.user.domain.type;

import com.tripleS.server.user.domain.User;
import jakarta.persistence.*;

@Entity
public class SelfAuth {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long selfAuthId;

    @Column (name = "self_id")
    private Long selfId;

    @Column (name = "self_pw")
    private String selfPw;

    @OneToOne
    @JoinColumn (name = "userId")
    private User userId;
}
