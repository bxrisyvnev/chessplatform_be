package org.example.chessplatformbe.persistence;

import org.example.chessplatformbe.domain.Article;
import org.example.chessplatformbe.domain.User;
import org.example.chessplatformbe.exceptions.InvalidUserException;

import java.util.Optional;

public interface ArticleRepository {
    Optional<Article> findById(Integer id);

    Article save(Article article);

    void delete(Article article);
}
