package com.tripleS.server.review.repository;

import com.tripleS.server.quiz.domain.QuizResult;
import com.tripleS.server.review.domain.Review;
import com.tripleS.server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends  JpaRepository<Review, Long> {
    List<Review> findByUser(User user);

}
