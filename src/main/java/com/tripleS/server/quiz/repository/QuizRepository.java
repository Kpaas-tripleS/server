package com.tripleS.server.quiz.repository;

import com.tripleS.server.quiz.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query(value = "SELECT * FROM quiz WHERE is_solved = false ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<Quiz> findRandomUnsolvedQuizzes(int count);


}