package com.tripleS.server.summary.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.jsoup.nodes.Document;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class NaverSearchService {

    private final WebClient webClient;
    private final Random random = new Random(); // 랜덤 인스턴스

    @Value("${naver.api.client-id}")
    private String clientId;

    @Value("${naver.api.client-secret}")
    private String clientSecret;

    @Value("${naver.api.url}")
    private String apiUrl;

    public NaverSearchService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<String> searchNews(String query) {

        int maxResults = 100;

        // URL을 직접 조립하여 전달
        String fullUrl = apiUrl + "?query=" + query + "&display=" + maxResults + "&sort=date";

        return webClient.get()
                .uri(fullUrl)  // 조립된 URL을 전달
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .retrieve()
                .bodyToMono(NaverSearchResponse.class)
                .map(response -> response.getItems().stream()
                        .filter(item -> item.getLink().contains("n.news.naver.com"))
                        .map(NaverSearchResponse.Item::getLink)  // 네이버 뉴스 URL을 가져옴
                        .collect(Collectors.toList()))
                .flatMap(urls -> {
                    // 랜덤으로 하나의 URL 선택
                    String randomUrl = urls.get(random.nextInt(urls.size()));
                    return Mono.just(randomUrl);
                });
    }

    // CSS 셀렉터를 사용해 기사의 본문을 추출
    public Mono<String> scrapeNewsContent(String url) {
        return Mono.fromCallable(() -> {
            Document doc = Jsoup.connect(url).get();

            // "newsct_article" 클래스 내에서 "dic_area" id를 가진 article 요소를 선택하여 본문 추출
            Element articleBody = doc.select("div.newsct_article article#dic_area").first();

            // 기사 내 불필요한 태그 제거 (예: 이미지 설명, 그림 등)
            if (articleBody != null) {
                articleBody.select("span.end_photo_org, em.img_desc, br, span.ore ").remove();
            }

            // 정제된 텍스트 추출
            String content = articleBody != null ? articleBody.text() : "";
            return content;

        });
    }

    // 검색어로부터 뉴스 본문을 추출하는 메서드
    public Mono<String> getNews(String query) {
        return searchNews(query)  // 검색을 통해 랜덤 뉴스 URL을 가져옴
                .flatMap(this::scrapeNewsContent);  // URL에 대해 본문을 추출
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Setter
    @Getter
    static class NaverSearchResponse {
        private List<Item> items;

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Setter
        @Getter
        static class Item {
            private String link;
            private String title;  // JSON 필드 추가
        }
    }
}
