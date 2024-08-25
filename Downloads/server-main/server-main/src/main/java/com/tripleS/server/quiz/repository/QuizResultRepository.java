package com.tripleS.server.quiz.repository;

import com.tripleS.server.quiz.domain.Quiz;
import com.tripleS.server.quiz.domain.QuizResult;
import com.tripleS.server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    List<QuizResult> findByUser(User user);

    List<QuizResult> findByQuiz(Quiz quiz);

    List<QuizResult> findByUserAndQuiz(User user, Quiz quiz);

}
