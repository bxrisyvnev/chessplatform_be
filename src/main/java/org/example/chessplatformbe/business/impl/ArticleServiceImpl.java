package org.example.chessplatformbe.business.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.chessplatformbe.business.IArticleService;
import org.example.chessplatformbe.controller.dto.request.CreateArticleDTO;
import org.example.chessplatformbe.controller.dto.response.ArticleResponceDTO;
import org.example.chessplatformbe.domain.Article;
import org.example.chessplatformbe.exceptions.InvalidArticleException;
import org.example.chessplatformbe.mapper.ArticleMapper;
import org.example.chessplatformbe.persistence.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements IArticleService {

    private final ArticleRepository articleRepository;

    @Override
    public Article getArticleById(Integer id) {
        Optional<Article> articleOpt = articleRepository.findById(id);
        return articleOpt.orElseThrow(() -> new IllegalArgumentException("Article not found with ID: " + id));
    }

    @Override
    public Map<String, java. lang. Object> getArticlePage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Article> articlePage = articleRepository.getArticlePage(pageable);

        List<ArticleResponceDTO> articles = articlePage.getContent().stream()
                .map(ArticleMapper::objectToResponse)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("articles", articles);
        response.put("currentPage", articlePage.getNumber());
        response.put("totalItems", articlePage.getTotalElements());
        response.put("totalPages", articlePage.getTotalPages());

        return response;
    }

    @Override
    public Article createArticle(CreateArticleDTO dto) {
        Article article = ArticleMapper.requestToObject(dto);
        return articleRepository.save(article);
    }

    @Override
    public Article updateArticle(CreateArticleDTO dto) {
        if (dto.getUpdateId() == null) {
            throw new IllegalArgumentException("Update ID must be provided");
        }

        Optional<Article> existing = articleRepository.findById(dto.getUpdateId());
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Article not found with ID: " + dto.getUpdateId());
        }

        Article updated = ArticleMapper.requestToObject(dto);
        return articleRepository.update(updated, dto.getUpdateId());
    }

    @Override
    public void deleteArticle(Integer articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article not found with ID: " + articleId));
        articleRepository.delete(article);
    }

    @Override
    public Article getByTitle(String title) throws InvalidArticleException {
        return articleRepository.findByTitle(title);
    }

    @Override
    public Map<String, Object> getArticlePageByTitle(String title, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Article> articlePage = articleRepository.getArticlesByTitlePage(title, pageable);

        List<ArticleResponceDTO> dtos = articlePage.getContent().stream()
                .map(ArticleMapper::objectToResponse)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("articles", dtos);
        response.put("currentPage", articlePage.getNumber());
        response.put("totalItems", articlePage.getTotalElements());
        response.put("totalPages", articlePage.getTotalPages());
        return response;
    }
}
