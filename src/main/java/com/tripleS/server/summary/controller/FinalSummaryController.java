package com.tripleS.server.summary.controller;

import com.tripleS.server.summary.service.FinalSummaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FinalSummaryController {

    private final FinalSummaryService finalSummaryService;

    public FinalSummaryController(FinalSummaryService finalSummaryService) {
        this.finalSummaryService = finalSummaryService;
    }

    @GetMapping("/final/summary")
    public Mono<ResponseEntity<String>> getSummary(
            @RequestParam String query,
            @RequestParam(defaultValue = "ko") String language,
            @RequestParam(defaultValue = "0") int tone,
            @RequestParam(defaultValue = "3") int summaryCount) {

        return finalSummaryService.getSummary(query, language, tone, summaryCount)
                .map(summary -> ResponseEntity.ok().body(summary))
                .onErrorResume(e -> {

                    // 에러 메시지 설정
                    String errorMessage = "An internal error occurred. Please try again later.";

                    // 예외의 종류에 따라 적절한 상태 코드 설정
                    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

                    if (e instanceof IllegalArgumentException) {
                        status = HttpStatus.BAD_REQUEST;
                        errorMessage = "Invalid request parameters.";
                    }

                    return Mono.just(ResponseEntity.status(status).body(errorMessage));
                });
    }
}
