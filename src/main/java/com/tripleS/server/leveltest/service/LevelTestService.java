package com.tripleS.server.leveltest.service;

import com.tripleS.server.leveltest.domain.Diagnosis;
import com.tripleS.server.leveltest.domain.LevelTest;
import com.tripleS.server.leveltest.domain.LevelTestResult;
import com.tripleS.server.leveltest.dto.request.LevelTestRequestDto;
import com.tripleS.server.leveltest.dto.response.LevelTestResponseDto;
import com.tripleS.server.leveltest.repository.DiagnosisRepository;
import com.tripleS.server.leveltest.repository.LevelTestRepository;
import com.tripleS.server.leveltest.repository.LevelTestResultRepository;
import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LevelTestService {
    @Autowired
    private LevelTestRepository levelTestRepository;

    @Autowired
    private LevelTestResultRepository levelTestResultRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    public List<LevelTest> getLevelTestQuestions() {
        return levelTestRepository.findTop10ByOrderByIdAsc();
    }

    @Transactional
    public LevelTestResponseDto submitLevelTest(Long userId, LevelTestRequestDto request) {
        if (levelTestResultRepository.existsByUserId(userId)) {
            throw new IllegalStateException("User has already taken the level test");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<LevelTest> questions = getLevelTestQuestions();
        int score = calculateScore(questions, request.getAnswers());
        LevelTestResponseDto diagnosisResult = getDiagnosis(score);

        LevelTestResult result = new LevelTestResult();
        result.setUser(user);
        result.setScore(score);
        result.setDiagnosis(diagnosisResult.getDiagnosis());
        result.setTestDate(LocalDateTime.now());
        levelTestResultRepository.save(result);

        return diagnosisResult;
    }

    private int calculateScore(List<LevelTest> questions, List<String> answers) {
        int correctAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getCorrectAnswer().equals(answers.get(i))) {
                correctAnswers++;
            }
        }
        return correctAnswers * 10;
    }

    private LevelTestResponseDto getDiagnosis(int score) {
        LevelTestResponseDto response = new LevelTestResponseDto();
        response.setScore(score);

        Diagnosis.DiagnosisLevel level;
        if (score <= 40) {
            level = Diagnosis.DiagnosisLevel.LOW;
        } else if (score <= 70) {
            level = Diagnosis.DiagnosisLevel.MEDIUM;
        } else {
            level = Diagnosis.DiagnosisLevel.HIGH;
        }

        Diagnosis diagnosis = diagnosisRepository.findByLevel(level);
        response.setDiagnosis(diagnosis.getLevel().toString());
        response.setFeedback(diagnosis.getFeedback());

        return response;
    }
}