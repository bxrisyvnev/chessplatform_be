package org.example.chessplatformbe.persistence;

import org.example.chessplatformbe.domain.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


import java.util.Optional;

public interface ArticleRepository {
    Optional<Article> findById(Integer id);

    Article save(Article article);

    void delete(Article article);

    Page<Article> getArticlePage(Pageable pageable);

    Article update(Article article, Integer updateId);
}
