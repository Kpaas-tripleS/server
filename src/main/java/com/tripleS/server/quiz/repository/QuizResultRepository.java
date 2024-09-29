package com.tripleS.server.quiz.repository;

import com.tripleS.server.quiz.domain.Quiz;
import com.tripleS.server.quiz.domain.QuizResult;
import com.tripleS.server.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    List<QuizResult> findByUser(User user);

    Optional<QuizResult> findByQuiz_QuizIdAndUser_Id(Long quizId, Long userId);

    List<QuizResult> findByUserAndQuizIsSolvedAndIsCorrect(User user, boolean isSolved, boolean isCorrect);

    long countByUser_IdAndQuiz_QuizIdAndIsCorrectFalse(Long userId, Long quizId);

    @Query("SELECT q FROM QuizResult q WHERE q.user.id = :userId AND q.isCorrect = false ORDER BY function('RAND')")
    List<QuizResult> findTop5ByUserIdAndIsCorrectFalseOrderByRandom(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT q FROM QuizResult q WHERE q.user.id = :userId AND q.isCorrect = false ORDER BY function('RAND')")
    List<QuizResult> findRandomIncorrectQuizzes(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT q FROM QuizResult q WHERE q.user.id = :userId AND q.isCorrect = false AND q.quiz.level = :difficulty ORDER BY function('RAND')")
    List<QuizResult> findRandomIncorrectQuizzesByDifficulty(@Param("userId") Long userId, @Param("difficulty") Quiz.DifficultyLevel difficulty, Pageable pageable);
}
