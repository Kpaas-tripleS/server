package com.tripleS.server.quiz.domain;

import com.tripleS.server.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Column(nullable = false)
    private String userAnswer;

    @Column(nullable = false)
    private Boolean isCorrect;

    @Column(nullable = false)
    private LocalDateTime answeredAt;

    @Builder
    public QuizResult(Quiz quiz, User user, String userAnswer, Boolean isCorrect) {
        this.quiz = quiz;
        this.userAnswer = userAnswer;
        this.isCorrect = isCorrect;
        this.answeredAt = LocalDateTime.now();
        this.user = user;

    }
}
