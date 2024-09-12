package com.tripleS.server.quiz.repository;


import com.tripleS.server.quiz.domain.QuizResult;
import com.tripleS.server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    List<QuizResult> findByUser(User user);
    List<QuizResult> findByUserAndIsCorrect(User user, Boolean isCorrect);
    Optional<QuizResult> findByQuizIdAndUserId(Long quizId, Long userId);
    List<QuizResult> findByUserAndQuizIsSolvedAndIsCorrect(User user, boolean isSolved, boolean isCorrect);

}
