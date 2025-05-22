package org.example.chessplatformbe.mapper;

import org.example.chessplatformbe.controller.dto.request.CreateArticleDTO;
import org.example.chessplatformbe.controller.dto.response.ArticleResponceDTO;
import org.example.chessplatformbe.domain.*;
import org.example.chessplatformbe.persistence.impl.jpa.entity.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArticleMapper {
    private ArticleMapper() {}

    public static Article entityToObject(ArticleEntity articleEntity) {
        Article article = new Article();
        article.setId(articleEntity.getId());
        article.setArticleTitle(articleEntity.getArticleTitle());
        article.setAuthorId(articleEntity.getUser().getId());
        article.setImageUrl(articleEntity.getImageUrl());
        article.setContentText(articleEntity.getContentText());

        // Convert List<CommentEntity> to List<Integer> (comment IDs)
        List<Integer> commentIds = articleEntity.getComments()
                .stream()
                .map(CommentEntity::getId)
                .toList();

        article.setCommentsIds(commentIds);

        return article;
    }

    public static ArticleEntity objectToEntity(Article article, UserEntity userEntity) {
        ArticleEntity articleEntity = new ArticleEntity();

        articleEntity.setArticleTitle(article.getArticleTitle());
        articleEntity.setImageUrl(article.getImageUrl());
        articleEntity.setContentText(article.getContentText());
        articleEntity.setUser(userEntity);

        List<CommentEntity> commentEntities = article.getCommentsIds().stream()
                .map(commentId -> {
                    CommentEntity comment = new CommentEntity();
                    comment.setId(commentId);
                    comment.setArticle(articleEntity);
                    return comment;
                })
                .toList();

        articleEntity.setComments(commentEntities);

        return articleEntity;
    }

    public static Article requestToObject(CreateArticleDTO dto) {
        Article article = new Article();
        article.setId(dto.getUpdateId()); // Used when updating
        article.setArticleTitle(dto.getArticleTitle());
        article.setImageUrl(dto.getImageUrl());
        article.setAuthorId(dto.getAuthorId());
        article.setContentText(dto.getContentText());
        article.setCommentsIds(dto.getCommentsIds() != null ? dto.getCommentsIds() : new ArrayList<>());
        return article;
    }

    public static ArticleResponceDTO objectToResponse(Article article) {
        ArticleResponceDTO dto = new ArticleResponceDTO();
        dto.setId(article.getId());
        dto.setArticleTitle(article.getArticleTitle());
        dto.setImageUrl(article.getImageUrl());
        dto.setAuthorId(article.getAuthorId());
        dto.setContentText(article.getContentText());
        dto.setCommentsIds(article.getCommentsIds());
        return dto;
    }
}
