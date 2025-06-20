package org.example.chessplatformbe.business.impl;

import lombok.RequiredArgsConstructor;
import org.example.chessplatformbe.business.IArticleService;
import org.example.chessplatformbe.domain.Article;
import org.example.chessplatformbe.domain.User;
import org.example.chessplatformbe.exceptions.InvalidArticleException;
import org.example.chessplatformbe.mapper.ArticleMapper;
import org.example.chessplatformbe.persistence.ArticleRepository;
import org.example.chessplatformbe.persistence.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements IArticleService {

    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;

    @Override
    public Article getArticleById(Integer id) {
        Optional<Article> articleOpt = articleRepository.findById(id);
        return articleOpt.orElseThrow(() -> new IllegalArgumentException("Article not found with ID: " + id));
    }

    @Override
    public Page<Article> getArticlePage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return articleRepository.getArticlePage(pageable);
    }

    @Override
    public Article createArticle(Article article) {
        Optional<User> user = userRepository.findById(article.getAuthorId());
        article.setAuthorName(user.get().getUsername());
        return articleRepository.save(article);
    }

    @Override
    public Article updateArticle(Article article, Integer updatedId) {
        if (updatedId == null) {
            throw new IllegalArgumentException("Update ID must be provided");
        }

        Optional<Article> existing = articleRepository.findById(updatedId);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Article not found with ID: " + updatedId);
        }

        return articleRepository.update(article, updatedId);
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
    public Page<Article> getArticlePageByTitle(String title, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return articleRepository.getArticlesByTitlePage(title, pageable);
    }
}
