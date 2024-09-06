package com.tripleS.server.summary.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ClovaSummaryService {

    private final WebClient webClient;

    @Value("${clova.api.client-id}")
    private String clientId;

    @Value("${clova.api.client-secret}")
    private String clientSecret;

    @Value("${clova.api.url}")
    private String apiUrl;

    public ClovaSummaryService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<String> summarizeContent(String content, String language, int summaryCount) {
        ClovaSummaryRequest.DocumentObject document = new ClovaSummaryRequest.DocumentObject(content);
        ClovaSummaryRequest.OptionObject option = new ClovaSummaryRequest.OptionObject(language, "news", 2, summaryCount);

        return webClient.post()
                .uri(apiUrl)
                .header("X-NCP-APIGW-API-KEY-ID", clientId)
                .header("X-NCP-APIGW-API-KEY", clientSecret)
                .bodyValue(new ClovaSummaryRequest(document, option))
                .retrieve()
                .bodyToMono(ClovaSummaryResponse.class)
                .map(ClovaSummaryResponse::getSummary);
    }

    @Setter
    @Getter
    static class ClovaSummaryRequest {
        private final DocumentObject document;
        private final OptionObject option;

        public ClovaSummaryRequest(DocumentObject document, OptionObject option) {
            this.document = document;
            this.option = option;
        }

        @Setter
        @Getter
        static class DocumentObject {
            private final String content;

            public DocumentObject(String content) {
                this.content = content;
            }
        }

        @Setter
        @Getter
        static class OptionObject {
            private final String language;
            private final String model;
            private final int tone;
            private final int summaryCount;

            public OptionObject(String language, String model, int tone, int summaryCount) {
                this.language = language;
                this.model = model != null ? model : "news"; // 기본값을 "news"로 설정
                this.tone = 2;
                this.summaryCount = summaryCount;
            }
        }
    }

    @Setter
    @Getter
    static class ClovaSummaryResponse {
        private String summary;
    }
}
