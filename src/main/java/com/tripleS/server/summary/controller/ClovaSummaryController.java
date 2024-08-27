package com.tripleS.server.summary.controller;

import com.tripleS.server.summary.service.ClovaSummaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ClovaSummaryController {

    private final ClovaSummaryService clovaSummaryService;

    public ClovaSummaryController(ClovaSummaryService clovaSummaryService) {
        this.clovaSummaryService = clovaSummaryService;
    }

    @GetMapping("/summary")
    public Mono<ResponseEntity<String>> summarize(
            @RequestParam String content,
            @RequestParam(defaultValue = "ko") String language,
            @RequestParam(defaultValue = "3") int summaryCount) {

        return clovaSummaryService.summarizeContent(content, language, summaryCount)
                .map(summary -> ResponseEntity.ok().body(summary))
                .onErrorResume(e -> {
                    // 에러 발생 시 로깅
                    System.err.println("Error occurred while summarizing content: " + e.getMessage());
                    e.printStackTrace();

                    // 에러 응답 반환
                    String errorMessage = "An internal error occurred while summarizing the content. Please try again later.";
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage));
                });
    }
}
