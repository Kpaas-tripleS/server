package com.tripleS.server.summary.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class NaverSearchService {

    private final WebClient webClient;

    @Value("${naver.api.client-id}")
    private String clientId;

    @Value("${naver.api.client-secret}")
    private String clientSecret;

    @Value("${naver.api.url}")
    private String apiUrl;

    public NaverSearchService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<List<String>> searchNews(String query) {

        return webClient.get()
                .uri(apiUrl + "?query=" + query)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .retrieve()
                .bodyToMono(NaverSearchResponse.class)
                .map(response -> response.getItems().stream()
                        .map(NaverSearchResponse.Item::getOriginallink)
                        .toList());
    }

    @Setter
    @Getter
    static class NaverSearchResponse {
        private List<Item> items;

        @Setter
        @Getter
        static class Item {
            private String originallink;

        }
    }
}
