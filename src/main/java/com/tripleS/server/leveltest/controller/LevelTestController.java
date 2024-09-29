package com.tripleS.server.leveltest.controller;

import com.tripleS.server.leveltest.domain.LevelTest;
import com.tripleS.server.leveltest.dto.request.LevelTestRequestDto;
import com.tripleS.server.leveltest.dto.response.LevelTestResponseDto;
import com.tripleS.server.leveltest.service.LevelTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/level-test")
public class LevelTestController {
    @Autowired
    private LevelTestService levelTestService;

    @GetMapping
    public ResponseEntity<List<LevelTest>> getLevelTestQuestions() {
        return ResponseEntity.ok(levelTestService.getLevelTestQuestions());
    }

    @PostMapping("/{userId}")
    public ResponseEntity<LevelTestResponseDto> submitLevelTest(
            @PathVariable Long userId,
            @RequestBody LevelTestRequestDto request) {
        return ResponseEntity.ok(levelTestService.submitLevelTest(userId, request));
    }

}