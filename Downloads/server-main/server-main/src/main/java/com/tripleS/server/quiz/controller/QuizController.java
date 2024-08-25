package com.tripleS.server.quiz.controller;

import com.tripleS.server.quiz.dto.quizAnswerDto;
import com.tripleS.server.quiz.dto.quizDto;
import com.tripleS.server.quiz.dto.quizResultDto;
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
    public ResponseEntity<List<quizDto>> getRandomQuizzes() {
        List<quizDto> quizzes = quizService.getRandomQuizzes(5);
        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<quizDto> getQuiz(@PathVariable Long quizId) {
        quizDto quiz = quizService.getQuiz(quizId);
        return ResponseEntity.ok(quiz);
    }

    @PostMapping("/{quizId}/answer")
    public ResponseEntity<quizResultDto> submitAnswer(@PathVariable Long quizId, @RequestBody quizAnswerDto answerDTO) {
        // userId를 answerDTO에서 가져와서 전달
        quizResultDto result = quizService.submitAnswer(quizId, answerDTO.getUserId(), answerDTO.getAnswer());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{quizId}/answer")
    public ResponseEntity<quizAnswerDto> getQuizAnswer(@PathVariable Long quizId) {
        String answer = quizService.getQuizAnswer(quizId);
        quizAnswerDto answerDTO = new quizAnswerDto();
        answerDTO.setAnswer(answer);
        return ResponseEntity.ok(answerDTO);
    }
}