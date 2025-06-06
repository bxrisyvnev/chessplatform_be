package org.example.chessplatformbe.persistence.impl.jpa;

import org.example.chessplatformbe.persistence.impl.jpa.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaComment extends JpaRepository<CommentEntity, Integer>{
    List<CommentEntity> findByArticle_Id(int id);

    @Query(value = "SELECT c.id, c.text, c.article_id FROM comments c JOIN users u ON c.user_id = u.id WHERE u.username = :username", nativeQuery = true)
    List<Object[]> findCommentsByUsername(@Param("username") String username);

}
