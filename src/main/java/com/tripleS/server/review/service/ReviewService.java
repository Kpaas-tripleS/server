package com.tripleS.server.review.service;

import com.tripleS.server.quiz.domain.QuizResult;
import com.tripleS.server.quiz.dto.QuizResultDto;
import com.tripleS.server.quiz.exception.ResourceNotFoundException;
import com.tripleS.server.quiz.repository.QuizRepository;
import com.tripleS.server.quiz.repository.QuizResultRepository;
import com.tripleS.server.quiz.service.QuizService;
import com.tripleS.server.review.dto.ReviewDto;
import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final QuizResultRepository quizResultRepository;
    private final UserRepository userRepository;
    private final QuizService quizService;
    private final QuizRepository quizRepository;

    @Autowired
    public ReviewService(QuizResultRepository quizResultRepository,
                         UserRepository userRepository,
                         QuizService quizService, QuizRepository quizRepository) {
        this.quizResultRepository = quizResultRepository;
        this.userRepository = userRepository;
        this.quizService = quizService;
        this.quizRepository = quizRepository;
    }


    @Transactional(readOnly = true)
    public List<ReviewDto> getReviewResult(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        List<QuizResult> reviewTargets = quizResultRepository.findByUserAndQuizIsSolvedAndIsCorrect(user, true, false);

        if (reviewTargets.isEmpty()) {
            return Collections.emptyList();
        }

        Collections.shuffle(reviewTargets);
        List<QuizResult> selectedResults = reviewTargets.stream()
                .limit(5)
                .collect(Collectors.toList());

        ReviewDto reviewDto = convertToReviewDto(user, selectedResults);
        return Collections.singletonList(reviewDto);
    }

    @Transactional(readOnly = true)
    public ReviewDto getReviewQuizByQuizId(Long quizId) {
        QuizResult quizResult = quizResultRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz result not found with id: " + quizId));
        return convertToReviewDto(quizResult.getUser(), Collections.singletonList(quizResult));
    }

    @Transactional
    public QuizResultDto submitReviewAnswer(Long quizId, Long userId, String answer) {
        QuizResult quizResult = quizResultRepository.findByQuiz_QuizIdAndUser_Id(quizId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz result not found for quizId: " + quizId + " and userId: " + userId));

        quizResult.update(answer);
        quizResultRepository.save(quizResult);

        return convertToQuizResultDto(quizResult);
    }

    private ReviewDto convertToReviewDto(User user, List<QuizResult> quizResults) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setUserId(user.getId());
        reviewDto.setReviewedAt(LocalDateTime.now());
        reviewDto.setQuizResults(quizResults.stream()
                .map(this::convertToQuizResultDto)
                .collect(Collectors.toList()));
        return reviewDto;
    }

    private QuizResultDto convertToQuizResultDto(QuizResult quizResult) {
        QuizResultDto dto = new QuizResultDto();
        dto.setQuizId(quizResult.getQuiz().getQuizId());  // 변경: getQuiz_id() -> getQuizId()
        dto.setUserId(quizResult.getUser().getId());
        dto.setUserAnswer(quizResult.getUserAnswer());
        dto.setIsCorrect(quizResult.getIsCorrect());
        dto.setAnsweredAt(quizResult.getAnsweredAt());
        dto.setResultId(quizResult.getResultId());  // Long to Integer conversion
        return dto;
    }
}