package com.tripleS.server.quiz.repository;

import com.tripleS.server.quiz.domain.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//quizresultRespository
@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
}
