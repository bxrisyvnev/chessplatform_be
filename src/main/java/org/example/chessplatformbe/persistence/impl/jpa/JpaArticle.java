package org.example.chessplatformbe.persistence.impl.jpa;

import org.example.chessplatformbe.persistence.impl.jpa.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface JpaArticle extends JpaRepository<ArticleEntity, Integer> {
    Page<ArticleEntity> findAll(Pageable pageable);
}