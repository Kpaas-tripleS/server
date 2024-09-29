package com.tripleS.server.quiz.domain;

import com.tripleS.server.review.domain.Review;
import com.tripleS.server.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Builder
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long resultId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review; // Review 필드 추가

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
    public QuizResult(Quiz quiz, User user, String userAnswer) {
        this.quiz = quiz;
        this.user = user;
        update(userAnswer);
    }

    // 매개변수가 없는 기본 생성자 추가 (JPA용)
    protected QuizResult() {
    }


    public void update(String userAnswer) {
        this.userAnswer = userAnswer;
        this.isCorrect = this.quiz.getAnswer().equalsIgnoreCase(userAnswer);
        this.answeredAt = LocalDateTime.now();
    }


    // Quiz의 ID를 가져오는 메서드
    public Long getQuizId() {

        if (this.quiz != null) {

            return this.quiz.getQuizId();
        } else {

            return null;
        }
    }

    // User의 ID를 가져오는 메서드
    public Long getUserId() {

        if (this.user != null) {

            return this.user.getId();
        } else {

            return null;
        }
    }

    //Review의 ID를 가져오는 메서드; 결과에 몇번 리뷰했는지 나타냄
    public Long getReviewId() {

        if (this.review != null) {

            return this.review.getReview_id();
        } else {

            return null;
        }
    }

    }


