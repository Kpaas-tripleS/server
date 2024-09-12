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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    public List<QuizDto> getRandomQuizzes(int count) {
        return quizRepository.findRandomUnsolvedQuizzes(count).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<QuizDto> getRandomQuizzesForMatch(int count) {
        return quizRepository.findRandomQuizzes(count).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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

        QuizResult quizResult = quizResultRepository.findByQuizIdAndUserId(quizId, userId)
                .orElse(new QuizResult(quiz, user, userAnswer));

        quizResult.update(userAnswer);

        // isSolved 업데이트 로직
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