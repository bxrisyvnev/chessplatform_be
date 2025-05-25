package org.example.chessplatformbe.persistence;

import org.example.chessplatformbe.domain.Article;
import org.example.chessplatformbe.domain.Comment;
import org.example.chessplatformbe.domain.User;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Optional<Comment> findById(Integer id);

    Comment save(Comment comment);

    void delete(Comment user);

    List<Comment> findByArticle_Id(Integer articleId);
}
