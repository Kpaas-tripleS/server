package com.tripleS.server.summary.controller;

import com.tripleS.server.summary.service.NaverSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class NaverSearchController {

    private final NaverSearchService naverSearchService;

    public NaverSearchController(NaverSearchService naverSearchService) {
        this.naverSearchService = naverSearchService;
    }

    @GetMapping("/search/news")
    public Mono<List<String>> searchNews(@RequestParam String query) {
        return naverSearchService.searchNews(query);
    }
}
