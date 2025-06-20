package org.example.chessplatformbe.business;

import org.example.chessplatformbe.domain.Article;
import org.example.chessplatformbe.exceptions.InvalidArticleException;
import org.springframework.data.domain.Page;

public interface IArticleService {

    Article getArticleById(Integer id);

    Page<Article> getArticlePage(Integer page, Integer size);

    Article createArticle(Article article);

    Article updateArticle(Article article, Integer updatedId);

    void deleteArticle(Integer articleId);

    Article getByTitle(String title) throws InvalidArticleException;

    Page<Article> getArticlePageByTitle(String title, Integer page, Integer size);
}
