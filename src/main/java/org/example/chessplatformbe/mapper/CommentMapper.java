package org.example.chessplatformbe.mapper;

import org.example.chessplatformbe.controller.dto.request.CreateCommentDTO;
import org.example.chessplatformbe.controller.dto.response.CommentResponceDTO;
import org.example.chessplatformbe.domain.Comment;
import org.example.chessplatformbe.persistence.impl.jpa.entity.ArticleEntity;
import org.example.chessplatformbe.persistence.impl.jpa.entity.CommentEntity;
import org.example.chessplatformbe.persistence.impl.jpa.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    private CommentMapper() {}

    public static Comment entityToObject(CommentEntity commentEntity) {
        Comment comment = new Comment();
        comment.setId(commentEntity.getId());
        comment.setText(commentEntity.getText());
        comment.setUserId(commentEntity.getUser().getId());
        comment.setArticleId(commentEntity.getArticle().getId());

        return comment;
    }

    public static CommentEntity objectToEntity(Comment comment, UserEntity user, ArticleEntity article) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setText(comment.getText());
        commentEntity.setArticle(article);
        commentEntity.setUser(user);

        return commentEntity;
    }

    public static Comment requestToObject(CreateCommentDTO dto) {
        Comment comment = new Comment();
        comment.setId(dto.getUpdateId()); // Used when updating
        comment.setText(dto.getText());
        comment.setUserId(dto.getUserId());
        comment.setArticleId(dto.getArticleId());

        return comment;
    }

    public static CommentResponceDTO objectToResponse(Comment comment) {
        CommentResponceDTO dto = new CommentResponceDTO();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setUserId(comment.getUserId());
        dto.setArticleId(comment.getArticleId());

        return dto;
    }
}
