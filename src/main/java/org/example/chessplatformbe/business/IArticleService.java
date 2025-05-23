package org.example.chessplatformbe.business;

import org.example.chessplatformbe.controller.dto.request.CreateArticleDTO;
import org.example.chessplatformbe.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

public interface IArticleService {

    Article getArticleById(Integer id);

    Map<String, Object> getArticlePage(Integer page, Integer size);

    Article createArticle(CreateArticleDTO dto);

    Article updateArticle(CreateArticleDTO dto);

    void deleteArticle(Integer articleId);
}
