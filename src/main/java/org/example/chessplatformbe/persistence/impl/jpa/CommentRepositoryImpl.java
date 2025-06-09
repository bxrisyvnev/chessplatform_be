package org.example.chessplatformbe.persistence.impl.jpa;

import org.example.chessplatformbe.domain.Comment;
import org.example.chessplatformbe.mapper.CommentMapper;
import org.example.chessplatformbe.persistence.CommentRepository;
import org.example.chessplatformbe.persistence.impl.jpa.entity.ArticleEntity;
import org.example.chessplatformbe.persistence.impl.jpa.entity.CommentEntity;
import org.example.chessplatformbe.persistence.impl.jpa.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final JpaComment jpaComment;
    private final JpaUser jpaUser;
    private final JpaArticle jpaArticle;

    @Autowired
    public CommentRepositoryImpl(JpaComment jpaComment, JpaUser jpaUser, JpaArticle jpaArticle) {
        this.jpaComment = jpaComment;
        this.jpaUser = jpaUser;
        this.jpaArticle = jpaArticle;
    }

    @Override
    public Optional<Comment> findById(Integer id) {
        Optional<CommentEntity> entityOptional = this.jpaComment.findById(id);

        if (entityOptional.isEmpty()) {
            throw  new IllegalArgumentException(String.valueOf(id));
        }

        Comment comment = CommentMapper.entityToObject(entityOptional.get());
        return Optional.of(comment);
    }

    @Override
    public Comment save(Comment comment) {
        UserEntity userEntity = jpaUser.findById(comment.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + comment.getUserId() + " not found"));

        ArticleEntity articleEntity = jpaArticle.findById(comment.getArticleId())
                .orElseThrow(() -> new IllegalArgumentException("Article with ID " + comment.getArticleId() + " not found"));

        CommentEntity commentEntity = CommentMapper.objectToEntity(comment, userEntity, articleEntity);
        CommentEntity saved = jpaComment.save(commentEntity);
        return CommentMapper.entityToObject(saved);
    }

    @Override
    public void delete(Comment comment) {
        jpaComment.deleteById(comment.getId());
    }

    @Override
    public List<Comment> findByArticleId(Integer articleId) {
        List<CommentEntity> commentEntities = jpaComment.findByArticle_Id(articleId);

        return commentEntities.stream()
                .map(CommentMapper::entityToObject)
                .toList();
    }

    @Override
    public List<Comment> findCommentsByUsername(String username) {
        List<Object[]> raw = jpaComment.findCommentsByUsername(username);
        return raw.stream().map(obj -> {
            Comment comment = new Comment();
            comment.setId((Integer) obj[0]);
            comment.setText((String) obj[1]);
            comment.setArticleId((Integer) obj[2]);
            return comment;
        }).toList();
    }
}
