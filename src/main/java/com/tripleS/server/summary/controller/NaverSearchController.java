package com.tripleS.server.summary.controller;

import com.tripleS.server.summary.service.NaverSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class NaverSearchController {

    private final NaverSearchService naverSearchService;

    public NaverSearchController(NaverSearchService naverSearchService) {
        this.naverSearchService = naverSearchService;
    }
    //for test
    @GetMapping("/url")
    public Mono<String> searchUrl(@RequestParam String query) {
        return naverSearchService.searchNews(query);
    }

    @GetMapping("/content")
    public Mono<String> searchContent(@RequestParam String url) {
        return naverSearchService.scrapeNewsContent(url);
    }

    @GetMapping("/news")
    public Mono<String> getNews(@RequestParam String query) {
        return naverSearchService.getNews(query);
    }
}
