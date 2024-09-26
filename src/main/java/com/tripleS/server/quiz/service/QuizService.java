package com.tripleS.server.quiz.service;

import com.tripleS.server.quiz.domain.Quiz;
import com.tripleS.server.quiz.domain.QuizResult;
import com.tripleS.server.quiz.dto.QuizDto;
import com.tripleS.server.quiz.dto.QuizResultDto;
import com.tripleS.server.quiz.exception.ResourceNotFoundException;
import com.tripleS.server.quiz.repository.QuizRepository;
import com.tripleS.server.quiz.repository.QuizResultRepository;
import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizResultRepository quizResultRepository;
    private final UserRepository userRepository;
    private final Random random = new Random();

    public List<QuizDto> getRandomQuizzes(int count) {
        long totalQuizzes = quizRepository.countAllQuizzes();
        return getRandomQuizzesWithOffset(count, totalQuizzes);
    }

    public List<QuizDto> getRandomQuizzesForMatch(int count) {
        long totalQuizzes = quizRepository.countAllQuizzes();
        return getRandomQuizzesWithOffset(count, totalQuizzes);
    }

    private List<QuizDto> getRandomQuizzesWithOffset(int count, long totalQuizzes) {
        if (totalQuizzes <= count) {
            return quizRepository.findQuizzesWithOffset(count, 0).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }

        long randomOffset = random.nextLong(totalQuizzes - count + 1);
        List<Quiz> quizzes = quizRepository.findQuizzesWithOffset(count, randomOffset);
        Collections.shuffle(quizzes);
        return quizzes.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public QuizDto getQuiz(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));
        return convertToDTO(quiz);
    }

    @Transactional
    public QuizResultDto submitAnswer(Long quizId, Long userId, String userAnswer) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        QuizResult quizResult = quizResultRepository.findByQuiz_QuizIdAndUser_Id(quizId, userId)
                .orElse(new QuizResult(quiz, user, userAnswer));

        quizResult.update(userAnswer);

        if (quizResult.getIsCorrect() && !quiz.getIsSolved()) {
            quiz.setIsSolved(true);
            quizRepository.save(quiz);
        }

        quizResultRepository.save(quizResult);

        return convertToResultDTO(quizResult);
    }

    public String getQuizAnswer(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));
        return quiz.getAnswer();
    }

    public List<QuizResultDto> getUserQuizResults(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return quizResultRepository.findByUser(user).stream()
                .map(this::convertToResultDTO)
                .collect(Collectors.toList());
    }

    private QuizDto convertToDTO(Quiz quiz) {
        QuizDto dto = new QuizDto();
        dto.setQuizId(quiz.getQuizId());
        dto.setQuestion(quiz.getQuestion());
        dto.setChoiceOne(quiz.getChoiceOne());
        dto.setChoiceTwo(quiz.getChoiceTwo());
        dto.setChoiceThree(quiz.getChoiceThree());
        dto.setChoiceFour(quiz.getChoiceFour());
        return dto;
    }

    private QuizResultDto convertToResultDTO(QuizResult result) {
        QuizResultDto dto = new QuizResultDto();
        dto.setQuizId(result.getQuiz().getQuizId());
        dto.setUserId(result.getUser().getId());
        dto.setUserAnswer(result.getUserAnswer());
        dto.setIsCorrect(result.getIsCorrect());
        dto.setAnsweredAt(result.getAnsweredAt());
        dto.setResultId(result.getResultId());
        return dto;
    }
}