package org.example.chessplatformbe.business;

import org.example.chessplatformbe.controller.dto.request.CreateCommentDTO;
import org.example.chessplatformbe.domain.Comment;

import java.util.List;

public interface ICommentService {

    Comment createComment(CreateCommentDTO dto);

    Comment updateComment(CreateCommentDTO dto);

    void deleteComment(Integer commentId);

    List<Comment> getCommentByArticleId(Integer articleId);
}
