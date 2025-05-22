package org.example.chessplatformbe.persistence.impl.jpa;

import org.example.chessplatformbe.persistence.impl.jpa.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaArticle extends JpaRepository<ArticleEntity, Integer> {
}