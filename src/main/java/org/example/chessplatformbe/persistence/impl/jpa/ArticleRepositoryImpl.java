package org.example.chessplatformbe.persistence.impl.jpa;

import org.example.chessplatformbe.domain.Article;
import org.example.chessplatformbe.exceptions.InvalidArticleException;
import org.example.chessplatformbe.exceptions.InvalidUserException;
import org.example.chessplatformbe.mapper.ArticleMapper;
import org.example.chessplatformbe.mapper.UserMapper;
import org.example.chessplatformbe.persistence.ArticleRepository;
import org.example.chessplatformbe.persistence.impl.jpa.entity.ArticleEntity;

import org.example.chessplatformbe.persistence.impl.jpa.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

    private final JpaArticle jpaArticle;
    private final JpaUser jpaUser;

    @Autowired
    public ArticleRepositoryImpl(JpaArticle jpaArticle, JpaUser jpaUser) {
        this.jpaArticle = jpaArticle;
        this.jpaUser = jpaUser;
    }

    @Override
    public Optional<Article> findById(Integer id) {
        Optional<ArticleEntity> entityOptional = this.jpaArticle.findById(id);

        if (entityOptional.isEmpty()) {
            throw  new IllegalArgumentException(String.valueOf(id));
        }

        Article article = ArticleMapper.entityToObject(entityOptional.get());
        return Optional.of(article);
    }

    @Override
    public Article save(Article article) {
        UserEntity userEntity = jpaUser.findById(article.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + article.getAuthorId() + " not found"));

        ArticleEntity articleEntity = ArticleMapper.objectToEntity(article, userEntity);
        ArticleEntity saved = jpaArticle.save(articleEntity);
        return ArticleMapper.entityToObject(saved);
    }

    @Override
    public Article update(Article article, Integer updateId) {
        UserEntity userEntity = jpaUser.findById(article.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + article.getAuthorId() + " not found"));

        ArticleEntity articleEntity = ArticleMapper.objectToEntity(article, userEntity);
        articleEntity.setId(updateId);
        ArticleEntity saved = jpaArticle.save(articleEntity);
        return ArticleMapper.entityToObject(saved);
    }

    @Override
    public void delete(Article article) {
        jpaArticle.deleteById(article.getId());
    }

    @Override
    public Page<Article> getArticlePage(Pageable pageable) {
        Page<ArticleEntity> entityPage = jpaArticle.findAll(pageable);

        List<Article> articles = entityPage.getContent()
                .stream()
                .map(ArticleMapper::entityToObject)
                .toList();

        return new PageImpl<>(articles, pageable, entityPage.getTotalElements());
    }

    @Override
    public Article findByTitle(String title) throws InvalidArticleException {
        ArticleEntity articleEntity = jpaArticle.findByArticleTitle(title);
        if (articleEntity == null) {
            throw new InvalidArticleException(title);
        }
        return ArticleMapper.entityToObject(articleEntity);
    }

    @Override
    public Page<Article> getArticlesByTitlePage(String title, Pageable pageable) {
        Page<ArticleEntity> entityPage =
                jpaArticle.findByArticleTitleContainingIgnoreCase(title, pageable);

        List<Article> articles = entityPage.getContent().stream()
                .map(ArticleMapper::entityToObject)
                .toList();

        return new PageImpl<>(articles, pageable, entityPage.getTotalElements());
    }
}
