package com.tripleS.server.review.domain;

import com.tripleS.server.user.domain.User;
import com.tripleS.server.quiz.domain.QuizResult;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long review_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime reviewedAt;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizResult> quizResults = new ArrayList<>();

    // 필요한 경우 추가 필드
    private Boolean isCompleted = false;
    private Integer score;


    public Review(User user) {
        this.user = user;
        this.reviewedAt = LocalDateTime.now();
    }
    // 기존 필드 getId와 같은 메서드 추가
    public Long getReviewId() {
        return this.review_id;
    }

    // 유저 ID 반환 메서드
    public Long getUserId() {
        return this.user.getId();
    }
}
