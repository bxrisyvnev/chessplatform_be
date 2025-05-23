package org.example.chessplatformbe.business.impl;

import lombok.RequiredArgsConstructor;
import org.example.chessplatformbe.business.IArticleService;
import org.example.chessplatformbe.controller.dto.request.CreateArticleDTO;
import org.example.chessplatformbe.controller.dto.response.ArticleResponceDTO;
import org.example.chessplatformbe.domain.Article;
import org.example.chessplatformbe.mapper.ArticleMapper;
import org.example.chessplatformbe.persistence.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements IArticleService {

    private final ArticleRepository articleRepository;

    public Article getArticleById(Integer id) {
        Optional<Article> articleOpt = articleRepository.findById(id);
        return articleOpt.orElseThrow(() -> new IllegalArgumentException("Article not found with ID: " + id));
    }


    public Article createArticle(CreateArticleDTO dto) {
        Article article = ArticleMapper.requestToObject(dto);
        return articleRepository.save(article);
    }

    public Article updateArticle(CreateArticleDTO dto) {
        if (dto.getUpdateId() == null) {
            throw new IllegalArgumentException("Update ID must be provided");
        }

        Optional<Article> existing = articleRepository.findById(dto.getUpdateId());
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Article not found with ID: " + dto.getUpdateId());
        }

        Article updated = ArticleMapper.requestToObject(dto);
        return articleRepository.save(updated);
    }

    public void deleteArticle(Integer articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article not found with ID: " + articleId));
        articleRepository.delete(article);
    }
}
