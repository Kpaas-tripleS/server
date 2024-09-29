package com.tripleS.server.review.service;

import com.tripleS.server.quiz.domain.Quiz;
import com.tripleS.server.quiz.domain.QuizResult;
import com.tripleS.server.quiz.dto.QuizResultDto;
import com.tripleS.server.quiz.exception.ResourceNotFoundException;
import com.tripleS.server.quiz.repository.QuizRepository;
import com.tripleS.server.quiz.repository.QuizResultRepository;
import com.tripleS.server.quiz.service.QuizService;
import com.tripleS.server.review.domain.Review;
import com.tripleS.server.review.dto.ReviewDto;
import com.tripleS.server.review.repository.ReviewRepository;
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
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(QuizResultRepository quizResultRepository,
                         UserRepository userRepository,
                         QuizService quizService, QuizRepository quizRepository, ReviewRepository reviewRepository) {
        this.quizResultRepository = quizResultRepository;
        this.userRepository = userRepository;
        this.quizService = quizService;
        this.quizRepository = quizRepository;
        this.reviewRepository = reviewRepository;
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

        ReviewDto reviewDto = convertToReviewDto(user, selectedResults); // User와 List<QuizResult>를 넘겨줌
        return Collections.singletonList(reviewDto);
    }

    @Transactional(readOnly = true)
    public ReviewDto getReviewQuizByQuizId(Long quizId) {
        QuizResult quizResult = quizResultRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz result not found with id: " + quizId));
        return convertToReviewDto(quizResult.getUser(), Collections.singletonList(quizResult)); // User와 List<QuizResult>를 넘겨줌
    }

    @Transactional
    public ReviewDto createReviewSession(Long userId, Quiz.DifficultyLevel difficulty) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        List<QuizResultDto> incorrectQuizzes;
        if (difficulty == Quiz.DifficultyLevel.RANDOM) {
            incorrectQuizzes = quizService.getRandomIncorrectQuizzes(userId, Quiz.DifficultyLevel.RANDOM, 5);
        } else {
            incorrectQuizzes = quizService.getRandomIncorrectQuizzes(userId, difficulty, 5);
        }

        if (incorrectQuizzes.isEmpty()) {
            throw new ResourceNotFoundException("No incorrect quizzes found for user with id: " + userId);
        }

        Review review = new Review(user);
        List<QuizResult> quizResults = incorrectQuizzes.stream()
                .map(this::convertToQuizResultEntity)
                .collect(Collectors.toList());
        review.setQuizResults(quizResults);
        reviewRepository.save(review);

        return convertToReviewDto(review, difficulty); // 기존 방식으로 호출
    }

    @Transactional
    public QuizResultDto submitReviewAnswer(Long quizId, Long userId, String answer) {
        QuizResult quizResult = quizResultRepository.findByQuiz_QuizIdAndUser_Id(quizId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz result not found for quizId: " + quizId + " and userId: " + userId));

        quizResult.update(answer);
        quizResultRepository.save(quizResult);

        return convertToQuizResultDto(quizResult);
    }

    private ReviewDto convertToReviewDto(Review review, Quiz.DifficultyLevel difficultyLevel) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getReview_id());
        reviewDto.setUserId(review.getUser().getId());
        reviewDto.setReviewedAt(review.getReviewedAt());
        reviewDto.setQuizResults(review.getQuizResults().stream()
                .map(this::convertToQuizResultDto)
                .collect(Collectors.toList()));
        reviewDto.setDifficultyLevel(difficultyLevel);
        return reviewDto;
    }

    // 새로운 메서드 추가
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
        dto.setQuizId(quizResult.getQuiz().getQuizId());
        dto.setUserId(quizResult.getUser().getId());
        dto.setUserAnswer(quizResult.getUserAnswer());
        dto.setIsCorrect(quizResult.getIsCorrect());
        dto.setAnsweredAt(quizResult.getAnsweredAt());
        dto.setResultId(quizResult.getResultId());
        return dto;
    }

    private QuizResult convertToQuizResultEntity(QuizResultDto quizResultDto) {
        Quiz quiz = quizRepository.findById(quizResultDto.getQuizId())
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizResultDto.getQuizId()));
        User user = userRepository.findById(quizResultDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + quizResultDto.getUserId()));

        QuizResult quizResult = new QuizResult(quiz, user, quizResultDto.getUserAnswer());
        quizResult.setIsCorrect(quizResultDto.getIsCorrect());
        quizResult.setAnsweredAt(quizResultDto.getAnsweredAt());
        return quizResult;
    }
}