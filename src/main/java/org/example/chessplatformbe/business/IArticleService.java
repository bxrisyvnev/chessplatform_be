package org.example.chessplatformbe.business;

import org.example.chessplatformbe.domain.Article;

import java.util.Optional;

public interface IArticleService {

    Optional<Article> getArticleById(Integer id);

    Article createArticle(Article article);

    Article updateArticle(Article article);

    void deleteArticle(Article article);
}
