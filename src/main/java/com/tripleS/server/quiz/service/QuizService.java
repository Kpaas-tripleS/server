package com.tripleS.server.quiz.service;

import com.tripleS.server.quiz.domain.Quiz;
import com.tripleS.server.quiz.domain.QuizResult;
import com.tripleS.server.quiz.dto.quizDto;
import com.tripleS.server.quiz.dto.quizResultDto;
import com.tripleS.server.quiz.exception.ResourceNotFoundException;
import com.tripleS.server.quiz.repository.QuizRepository;
import com.tripleS.server.quiz.repository.QuizResultRepository;
import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuizResultRepository quizResultRepository;
    private final UserRepository userRepository;

    public QuizService(QuizRepository quizRepository, QuizResultRepository quizResultRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.quizResultRepository = quizResultRepository;
        this.userRepository = userRepository;
    }

    public List<quizDto> getRandomQuizzes(int count) {
        return quizRepository.findRandomUnsolvedQuizzes(count).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<quizDto> getRandomQuizzesForMatch(int count) {
        return quizRepository.findRandomQuizzes(count).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public quizDto getQuiz(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));
        return convertToDTO(quiz);
    }

    @Transactional
    public quizResultDto submitAnswer(Long quizId, Long userId, String userAnswer) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        boolean isCorrect = quiz.getAnswer().equalsIgnoreCase(userAnswer);

        QuizResult result = QuizResult.builder()
                .quiz(quiz)
                .user(user)
                .userAnswer(userAnswer)
                .isCorrect(isCorrect)
                .build();

        quizResultRepository.save(result);

        if (isCorrect) {
            quiz.setIsSolved(true);
            quizRepository.save(quiz);
        }

        return convertToResultDTO(result);
    }

    public String getQuizAnswer(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));
        return quiz.getAnswer();
    }

    public List<quizResultDto> getUserQuizResults(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return quizResultRepository.findByUser(user).stream()
                .map(this::convertToResultDTO)
                .collect(Collectors.toList());
    }

    private quizDto convertToDTO(Quiz quiz) {
        quizDto dto = new quizDto();
        dto.setId(quiz.getId());
        dto.setQuestion(quiz.getQuestion());
        dto.setChoiceOne(quiz.getChoiceOne());
        dto.setChoiceTwo(quiz.getChoiceTwo());
        dto.setChoiceThree(quiz.getChoiceThree());
        dto.setChoiceFour(quiz.getChoiceFour());
        return dto;
    }

    private quizResultDto convertToResultDTO(QuizResult result) {
        quizResultDto dto = new quizResultDto();
        dto.setQuizId(result.getQuiz().getId());
        dto.setUserId(result.getUser().getId());
        dto.setUserAnswer(result.getUserAnswer());
        dto.setIsCorrect(result.getIsCorrect());
        dto.setAnsweredAt(result.getAnsweredAt());
        return dto;
    }

}