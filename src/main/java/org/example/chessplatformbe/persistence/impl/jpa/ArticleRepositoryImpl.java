package org.example.chessplatformbe.persistence.impl.jpa;

import org.example.chessplatformbe.domain.Article;
import org.example.chessplatformbe.mapper.ArticleMapper;
import org.example.chessplatformbe.persistence.ArticleRepository;
import org.example.chessplatformbe.persistence.impl.jpa.entity.ArticleEntity;

import org.example.chessplatformbe.persistence.impl.jpa.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public void delete(Article article) {
        jpaArticle.deleteById(article.getId());
    }
}
