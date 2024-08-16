package com.tripleS.server.summary.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article_file_url", nullable = false)
    private String articleFileUrl;

    @Column(name = "ai_summary", nullable = false)
    private String aiSummary;

    @Builder
    public Article(String articleFileUrl, String aiSummary) {
        this.articleFileUrl = articleFileUrl;
        this.aiSummary = aiSummary;
    }
}
