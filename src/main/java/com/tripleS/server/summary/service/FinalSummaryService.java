package com.tripleS.server.summary.service;

import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;

@Service
public class FinalSummaryService {

    private final NaverSearchService naverSearchService;
    private final ClovaSummaryService clovaSummaryService;

    public FinalSummaryService(NaverSearchService naverSearchService, ClovaSummaryService clovaSummaryService) {
        this.naverSearchService = naverSearchService;
        this.clovaSummaryService = clovaSummaryService;
    }

    public Mono<String> getSummary(String query, String language, int tone, int summaryCount) {
        return naverSearchService.searchNews(query)
                .flatMap(newsContent ->
                        naverSearchService.scrapeNewsContent(newsContent)
                )
                .flatMap(content ->
                        clovaSummaryService.summarizeContent(content, language, summaryCount)
                )
                .onErrorResume(e -> {
                    // 에러 발생 시 로깅
                    System.err.println("Error occurred in FinalSummaryService: " + e.getMessage());
                    e.printStackTrace();

                    // 에러 처리 및 사용자에게 적절한 메시지 반환
                    return Mono.error(new RuntimeException("Failed to process summary request"));
                });
    }
}
