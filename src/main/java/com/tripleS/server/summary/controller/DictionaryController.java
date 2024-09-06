package com.tripleS.server.summary.controller;

import com.tripleS.server.summary.dto.DefinitionResponse;
import com.tripleS.server.summary.service.DictionaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class DictionaryController {

    private final DictionaryService dictionaryService;

    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/word")
    public Mono<List<DefinitionResponse>> getDefinition(@RequestParam String word) {
        return dictionaryService.searchDefinitions(word);
    }
}