package org.example.chessplatformbe.business;

import org.example.chessplatformbe.controller.dto.request.CreateArticleDTO;
import org.example.chessplatformbe.domain.Article;

import java.util.Optional;

public interface IArticleService {

    Article getArticleById(Integer id);

    Article createArticle(CreateArticleDTO dto);

    Article updateArticle(CreateArticleDTO dto);

    void deleteArticle(Integer articleId);
}
