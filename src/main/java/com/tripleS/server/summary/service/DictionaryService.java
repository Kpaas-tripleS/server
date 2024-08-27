package com.tripleS.server.summary.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripleS.server.summary.dto.DefinitionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class DictionaryService {

    @Value("${dic.api.key}")
    private String apikey;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public DictionaryService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://stdict.korean.go.kr/api").build();
        this.objectMapper = new ObjectMapper(); // ObjectMapper 초기화
    }

    public Mono<String> searchWord(String word) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search.do")
                        .queryParam("key", apikey)
                        .queryParam("q", word)
                        .queryParam("req_type", "json")
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }

    // 새로운 메서드 추가: sup_no와 definition만 추출
    public Mono<List<DefinitionResponse>> searchDefinitions(String word) {
        return searchWord(word)
                .map(this::extractDefinitions);
    }

    private List<DefinitionResponse> extractDefinitions(String jsonResponse) {
        List<DefinitionResponse> definitions = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode items = root.path("channel").path("item");

            if (items.isArray()) {
                for (JsonNode item : items) {
                    String supNo = item.path("sup_no").asText();
                    String definition = item.path("sense").path("definition").asText();
                    definitions.add(new DefinitionResponse(supNo, definition));
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // 오류 처리
        }
        return definitions;
    }
}
