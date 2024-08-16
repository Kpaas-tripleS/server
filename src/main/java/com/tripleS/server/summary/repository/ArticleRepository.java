package com.tripleS.server.summary.repository;

import com.tripleS.server.summary.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//articleRespository
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
