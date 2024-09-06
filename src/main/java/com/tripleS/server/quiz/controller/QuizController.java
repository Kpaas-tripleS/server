package com.tripleS.server.quiz.controller;

import com.tripleS.server.quiz.dto.QuizAnswerDto;
import com.tripleS.server.quiz.dto.QuizDto;
import com.tripleS.server.quiz.dto.QuizResultDto;
import com.tripleS.server.quiz.service.QuizService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
@Tag(name = "Quiz", description = "Quiz management APIs")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public ResponseEntity<List<QuizDto>> getRandomQuizzes() {
        List<QuizDto> quizzes = quizService.getRandomQuizzes(5);
        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<QuizDto> getQuiz(@PathVariable Long quizId) {
        QuizDto quiz = quizService.getQuiz(quizId);
        return ResponseEntity.ok(quiz);
    }

    @PostMapping("/{quizId}/answer")
    public ResponseEntity<QuizResultDto> submitAnswer(@PathVariable Long quizId, @RequestBody QuizAnswerDto answerDTO) {
        // userId를 answerDTO에서 가져와서 전달
        QuizResultDto result = quizService.submitAnswer(quizId, answerDTO.getUserId(), answerDTO.getAnswer());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{quizId}/answer")
    public ResponseEntity<QuizAnswerDto> getQuizAnswer(@PathVariable Long quizId) {
        String answer = quizService.getQuizAnswer(quizId);
        QuizAnswerDto answerDTO = new QuizAnswerDto();
        answerDTO.setAnswer(answer);
        return ResponseEntity.ok(answerDTO);
    }
}