package com.tripleS.server.quiz.domain;

import com.tripleS.server.review.domain.Review;
import com.tripleS.server.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private Long resultId;

    @Column(name = "quiz_id", insertable = false, updatable = false)
    private Long quizId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(nullable = false)
    private String userAnswer;

    @Column(nullable = false)
    private Boolean isCorrect;

    @Column(nullable = false)
    private LocalDateTime answeredAt;

    @Builder
    public QuizResult(Quiz quiz, User user, String userAnswer) {
        this.quiz = quiz;
        this.user = user;
        this.quizId = quiz.getQuizId();
        this.userId = user.getId();
        update(userAnswer);

    }

    public void update(String userAnswer) {
        this.userAnswer = userAnswer;
        this.isCorrect = this.quiz.getAnswer().equalsIgnoreCase(userAnswer);
        this.answeredAt = LocalDateTime.now();
    }
}