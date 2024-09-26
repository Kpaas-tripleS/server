package com.tripleS.server.quiz.repository;

import com.tripleS.server.quiz.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Query(value = "SELECT COUNT(*) FROM quiz", nativeQuery = true)
    long countAllQuizzes();

    @Query(value = "SELECT * FROM quiz LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Quiz> findQuizzesWithOffset(@Param("limit") int limit, @Param("offset") long offset);

    @Query(value = "SELECT COUNT(*) FROM quiz WHERE is_solved = false", nativeQuery = true)
    long countUnsolvedQuizzes();

    @Query(value = "SELECT * FROM quiz WHERE is_solved = false LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Quiz> findUnsolvedQuizzesWithOffset(@Param("limit") int limit, @Param("offset") long offset);
}