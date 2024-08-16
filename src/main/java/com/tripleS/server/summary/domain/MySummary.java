package com.tripleS.server.summary.domain;

import com.tripleS.server.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//mysummary
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MySummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "my_summary", nullable = false)
    private String mySummary;

    @Column(name = "is_long", nullable = false)
    private Boolean isLong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public MySummary(String mySummary, Boolean isLong, Article article, User user) {
        this.mySummary = mySummary;
        this.isLong = isLong;
        this.article = article;
        this.user = user;
    }
}
