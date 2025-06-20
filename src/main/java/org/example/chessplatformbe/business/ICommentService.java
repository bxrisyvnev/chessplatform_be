package org.example.chessplatformbe.business;

import org.example.chessplatformbe.domain.Comment;

import java.util.List;

public interface ICommentService {

    Comment createComment(Comment comment);

    Comment updateComment(Comment comment, Integer updateId);

    void deleteComment(Integer commentId);

    List<Comment> getCommentByArticleId(Integer articleId);

    List<Comment> getCommentByAuthorUsername(String username);
}
